package com.boyoi.base.web.controller;

import com.boyoi.base.domain.LogLogin;
import com.boyoi.base.domain.Role;
import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.enums.UserStatusEnum;
import com.boyoi.base.security.LoginFailHelper;
import com.boyoi.base.service.DepartmentService;
import com.boyoi.base.service.LogLoginService;
import com.boyoi.base.service.UserService;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.EncryptUtil;
import com.boyoi.core.utils.RedisUtil;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.core.web.controller.BaseCrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 用户管理Controller
 *
 * @author Chenj
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseCrudController<User, UserService> {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private LogLoginService logLoginService;

    @Override
    protected void addUiInit(User user, Model model) {
        model.addAttribute("userStatus", UserStatusEnum.values());
        model.addAttribute("departmentList", departmentService.findAll());
    }

    @Override
    public void addFunBefore(User user, String... other) {
        //去帐号前后空格
        user.setLoginName(user.getLoginName().trim());
        //设置用户id
        user.setGuid(WebUtils.generateID());
        //sha1加密两次
        user.setPassword(EncryptUtil.current(user.getPassword()));
    }

    @Override
    protected void updateUiInit(Model model) {
        model.addAttribute("departmentList", departmentService.findAll());
    }

    @Override
    public void updateBefore(User user, String... other) {
        String password = user.getPassword();
        if (null != password && !"".equals(password.trim())) {
            user.setPassword(EncryptUtil.current(password));
        } else {
            user.setPassword(null);
        }
        super.updateBefore(user, other);
    }

    @Override
    protected void beforeListAll(Model model) {
        model.addAttribute("userStatus", UserStatusEnum.values());
        super.beforeListAll(model);
    }

    /**
     * 修改密码,界面
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePasswordUI(String userId, HttpSession session, Model model) {
        if (null != userId && !"".equals(userId)) {
            User user = (User) session.getAttribute(LoginFlag.LOGIN_SUCCESS.name());
            //session中登录用户的id等于传来的id,才进行修改密码操作
            if (userId.equals(user.getGuid())) {
                model.addAttribute("username", user.getLoginName());
                model.addAttribute("guid", userId);
                return "User/changePassword";
            }
        }
        return "error";
    }

    /**
     * 修改密码,功能
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult changePasswordFun(User user, String passwordOld, Model model) {
        responseResult.setStatus(false);
        model.addAttribute("guid", user.getGuid());
        model.addAttribute("username", user.getLoginName());
        // 检验原密码是否正确
        User userOld = new User();
        userOld.setLoginName(user.getLoginName());
        userOld.setPassword(passwordOld);
        if (null == userService.findUserByUsernamePwd(userOld)) {
            log.info("用户:{},修改密码失败, 原密码错误!", user.getLoginName());
            responseResult.setMsg("原密码错误, 请重新输入原密码!");
            return responseResult;
        }

        String password = user.getPassword();
        if (null != user.getGuid() && !"".equals(user.getGuid()) && null != password && !"".equals(password.trim())) {
            //sha1加密两次
            user.setPassword(EncryptUtil.current(user.getPassword()));
            int result = userService.changePassword(user);
            if (result == 1) {
                log.info("用户:{},修改密码成功", user.getLoginName());
                responseResult.setStatus(true);
                responseResult.setMsg("恭喜,修改密码成功!");
                return responseResult;
            }
        }
        log.info("用户:{},修改密码失败", user.getLoginName());
        responseResult.setMsg("修改密码失败,请稍后在试!");

        return responseResult;
    }

    /**
     * 授权(视图)
     *
     * @param userId 用户ID
     */
    @RequestMapping(value = "granAuth", method = RequestMethod.GET)
    public String granAuth(String userId, Model model) {
        // 查找没有授权的角色
        List<Role> noGranRole = service.findNoGranRoleByUserId(userId);
        // 查找授权了的角色
        List<Role> granRole = service.findGranRoleByUserId(userId);

        model.addAttribute("user", service.findByGuid(userId));
        model.addAttribute("noGranRole", noGranRole);
        model.addAttribute("granRole", granRole);
        return url + "/granAuth";
    }

    /**
     * 添加授权(功能)
     *
     * @param userId 角色ID
     */
    @RequestMapping(value = "granAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult granAuthFun(String userId, String method, String roleIds[]) {
        if ("add".equals(method)) {
            Integer success = service.addRole(userId, roleIds);
            responseResult.setStatus(true);
            responseResult.setMsg("成功授权" + success + "个角色！");
        } else if ("del".equals(method)) {
            Integer success = service.delRole(userId, roleIds);
            responseResult.setStatus(true);
            responseResult.setMsg("成功删除" + success + "个角色！");
        }
        return responseResult;
    }

    /**
     * 登录界面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        //用户重新get请求
        Integer login_error_time = LoginFailHelper.getTimes(request.getRemoteAddr());
        //超过3次,返回带有验证码的视图
        return login_error_time > 3 ? "User/loginWithValidateCode" : "User/login";
    }

    /**
     * 管理界面
     *
     * @return 视图
     */
    @RequestMapping("admin")
    public String admin(Model model) {
        return "User/admin";
    }

    @RequestMapping("sessionTimeout")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax 超时处理
            response.setStatus(301);
            response.setHeader("sessionStatus", "timeout");
            PrintWriter out = response.getWriter();
            out.print("{\"status\":false, \"msg\":\"登录超时\"}");
            out.flush();
            out.close();
        } else { // http 超时处理
            response.setHeader("sessionStatus", "timeout");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return String
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 保存退出时间
        saveUserLoginLog();
        // 使session失效
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 记录用户登录日志
     * 保存在session中, 用户退出时
     */
    private void saveUserLoginLog() {

        LogLogin logLogin = (LogLogin) session.getAttribute(LoginFlag.USER_LOGIN_LOG.name());
        Date date = new Date();
        logLogin.setLogoutDate(date);
        // 停留时间(转化为秒)
        logLogin.setLeaveTime((date.getTime() - logLogin.getLoginDate().getTime()) / 1000);
        logLoginService.updateByNotEmpty(logLogin);

    }

    /**
     * 更新当前用户风格
     *
     * @param customStyle 风格
     * @return json
     */
    @RequestMapping(value = "saveCustomStyle")
    @ResponseBody
    public ResponseResult saveCustomStyle(Integer customStyle) {
        User user = new User();
        user.setGuid(getUser().getGuid());
        user.setCustomStyle(customStyle);
        int i = service.updateByNotEmpty(user);
        if (i == 1) {
            // 更新session
            User userInSession = getUser();
            userInSession.setCustomStyle(customStyle);
            session.setAttribute(LoginFlag.LOGIN_SUCCESS.name(), userInSession);
            responseResult.setStatus(true);
            responseResult.setMsg("更新风格成功!");
        } else {
            responseResult.setStatus(false);
            responseResult.setMsg("更新风格失败!");
        }
        return responseResult;
    }

    /**
     * 提供接口。获得用户信息
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    @ResponseBody
    @RequestMapping(value = "userInfo")
    public Object getUserInfo(String loginName) {
        if (null != loginName && !"".equals(loginName.trim())) {
            User user = service.findUserByUsername(loginName);
            Map<String, Object> result = new HashMap<>();
            try {
                result.put("realName", user.getRealName());
                result.put("deptName", user.getDepartment().getDeptName());
                result.put("telephone", user.getTelephone());
                result.put("email", user.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            responseResult.setStatus(false);
            responseResult.setMsg("用户名不能为空!");
            return responseResult;
        }
    }

    /**
     * 清除Redis缓存
     */
    @ResponseBody
    @RequestMapping(value = "clearCache")
    public ResponseResult clearRedisCache() {
        if (RedisUtil.clearCache()){
            responseResult.setStatus(true);
            responseResult.setMsg("成功清除Redis缓存!");
        }else {
            responseResult.setStatus(false);
            responseResult.setMsg("清除Redis缓存失败!请稍后在试!");
        }
        return responseResult;
    }

    /**
     * 认证密码
     */
    @RequestMapping(value = "authPassword", method = RequestMethod.GET)
    public String authPassword(String userId, Model model) {
        model.addAttribute("userId", userId);
        return "User/authPassword";
    }

    /**
     * 认证密码
     */
    @RequestMapping(value = "authPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult authPasswordPOST(String authPassword, String userId) {
        if (null != userId && !"".equals(userId) && null != authPassword && !"".equals(authPassword)){
            boolean b = service.setAuthPassword(EncryptUtil.current(authPassword), userId);
            if (b){
                responseResult.setStatus(true);
                responseResult.setMsg("设置成功！");
            }else {
                responseResult.setStatus(false);
                responseResult.setMsg("设置失败！");
            }
        }
        return responseResult;
    }

}
