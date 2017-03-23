package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.Role;
import com.boyoi.base.service.ModuleService;
import com.boyoi.base.service.RoleService;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"role", "role" })
public class RoleController extends BaseCrudController<Role,RoleService> {

    @Resource
    private ModuleService moduleService;

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param role 角色管理实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(Role role, Model model) {

    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {

    }

    /**
     * 授权(视图)
     * @param roleId 角色ID
     */
    @RequestMapping(value = "granAuth", method = RequestMethod.GET)
    public String granAuth(String roleId, Model model){
        // 查找所有的模块
        List<Module> noGranModule = service.findNoGranModuleByRoleId(roleId);
        // 查找对应已经授权的模块
        List<Module> moduleByRoleId = service.findModuleByRoleId(roleId);

        model.addAttribute("role", service.findByGuid(roleId));
        model.addAttribute("noGranModule", noGranModule);
        model.addAttribute("granModule", moduleByRoleId);
        return url + "/granAuth";
    }

    /**
     * 添加授权(功能)
     * @param roleId 角色ID
     */
    @RequestMapping(value = "granAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult granAuthFun(String roleId, String method, String moduleIds[]){
        if ("add".equals(method)){
            Integer success = service.addModule(roleId, moduleIds);
            responseResult.setStatus(true);
            responseResult.setMsg("成功授权" + success + "个模块！");
        }else if ("del".equals(method)){
            Integer success = service.delModule(roleId, moduleIds);
            responseResult.setStatus(true);
            responseResult.setMsg("成功删除" + success + "个模块！");
        }
        return responseResult;
    }

}