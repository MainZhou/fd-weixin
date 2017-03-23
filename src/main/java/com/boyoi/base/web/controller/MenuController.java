package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.User;
import com.boyoi.base.service.IconService;
import com.boyoi.base.service.MenuService;
import com.boyoi.base.service.UserService;
import com.boyoi.core.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"menu", "menu" })
public class MenuController extends BaseCrudController<Menu,MenuService> {

    @Autowired
    private UserService userService;

    @Autowired
    private IconService iconService;


    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param menu 菜单实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(Menu menu, Model model) {
        model.addAttribute("parentMenuList", service.findLevel1Menu());
        model.addAttribute("icons", iconService.findAll());
    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {
        model.addAttribute("parentMenuList", service.findLevel1Menu());
        model.addAttribute("icons", iconService.findAll());
    }

    /**
     * 加载当前登录用户的菜单
     */
    @RequestMapping(value = "loadMenu")
    @ResponseBody
    public List<Menu> loadUserMenu(){
        // 获得当前登录用户
        User user = getUser();
        // 返回用户获得的所有授权菜单
        return userService.findMenuByUserId(user.getGuid());
    }

    /**
     * 查找所有的子菜单
     * @param parentId 父菜单ID
     * @return 子菜单JSON
     */
    @RequestMapping(value = "findChildMenu")
    @ResponseBody
    public List<Menu> findChildMenuByParentMenuId(String parentId){
        return service.findChildMenuByParentMenuId(parentId);
    }
}