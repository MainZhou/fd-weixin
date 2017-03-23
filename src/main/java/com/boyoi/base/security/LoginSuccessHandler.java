package com.boyoi.base.security;

import com.boyoi.base.domain.LogLogin;
import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.service.LogLoginService;
import com.boyoi.base.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 登录成功处理器
 * 设置默认国际化
 * @author Chenj
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private UserService userService;
    // 记录登录日志
    private LogLoginService logLoginService;

    public LoginSuccessHandler() {
        super("/user/admin");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 删除失败记录。下次不要在输入验证码
        LoginFailHelper.deleteTimes(request.getRemoteAddr());
        // 默认中文
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.CHINA);

        // 获得用户对象
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        // 把用户信息、菜单存入session中
        saveUserMenu2Session(user, request);

        // 记录用户登录日志
        saveUserLoginLog(user, request);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    /**
     * 记录用户登录日志
     * 保存在session中, 用户退出时
     * @param user 用户
     */
    private void saveUserLoginLog(User user, HttpServletRequest request) {
        LogLogin logLogin = new LogLogin();
        logLogin.setGuid(com.boyoi.core.utils.WebUtils.generateID());
        logLogin.setUser(user);
        String Agent = request.getHeader("User-Agent");
        UserAgent userAgent = new UserAgent(Agent);
        logLogin.setBrowser(userAgent.getBrowser().toString().toLowerCase() +
                            " - " +
                            userAgent.getOperatingSystem().getName().toLowerCase());
        String ip = request.getHeader("X-Real-IP");
        if (null == ip)
            ip = request.getHeader("X-Forwarded-For");
        if (null == ip)
            ip = request.getRemoteAddr();
        logLogin.setIp(ip);
        logLogin.setLoginDate(new Date());
        logLoginService.add(logLogin);
        // 存入session中, 退出根据guid写入退出时间
        request.getSession().setAttribute(LoginFlag.USER_LOGIN_LOG.name(), logLogin);
    }


    /**
     * 把用户信息存入session中
     * 保存用户菜单到session中
     */
    private void saveUserMenu2Session(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();

        //把用户信息存入session中
        session.setAttribute(LoginFlag.LOGIN_SUCCESS.name(), user);

        // session 中存放用户对应的所有菜单
        List<Menu> menus = userService.findMenuByUserId(user.getGuid());
        // 存放新的菜单，父菜单下带子菜单
        List<Menu> newMenus = new ArrayList<>();
        for (Menu menu : menus){
            // 父菜单不存在, 说明是顶级菜单
            if(null == menu.getParentMenu()){
                newMenus.add(menu);
            }else {
                // 子菜单
                int index = newMenus.indexOf(menu.getParentMenu());
                // 存在,添加到子菜单集合中
                if (index != -1){
                    newMenus.get(index).getSubMenus().add(menu);
                }else {
                    // 不存在, 添加一个新的父菜单
                    Menu parentMenu = menu.getParentMenu();
                    parentMenu.getSubMenus().add(menu);
                    newMenus.add(parentMenu);
                }
            }
        }
        session.setAttribute(LoginFlag.USER_MENU.name(), newMenus);
        log.info("{}用户登录成功!IP:{}", user.getLoginName(), request.getRemoteAddr());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLogLoginService(LogLoginService logLoginService) {
        this.logLoginService = logLoginService;
    }
}
