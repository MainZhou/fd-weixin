package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.Module;
import com.boyoi.base.service.MenuService;
import com.boyoi.base.service.ModuleService;
import com.boyoi.core.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模块 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"module", "module" })
public class ModuleController extends BaseCrudController<Module,ModuleService> {

    @Resource
    private MenuService menuService;

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param module 模块实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(Module module, Model model) {
        model.addAttribute("level1MenuList", menuService.findLevel1Menu());

    }

    /**
     * 进入编辑对象前,需要初始化的一些数据
     * @param model 模型
     */
    @Override
    protected void updateUiInit(Model model) {

        List<Menu> level1Menu = menuService.findLevel1Menu();
        model.addAttribute("level1MenuList", level1Menu);
        //获得Module对象
        Module update = (Module) model.asMap().get("update");
        //通过Module对象的父菜单，查找所有的子菜单
            if (null == update.getMenu() && level1Menu.size() > 0){
                model.addAttribute("childMenuList", menuService.findChildMenuByParentMenuId(level1Menu.get(0).getGuid()));
            }else {
                model.addAttribute("childMenuList", menuService.findChildMenuByParentMenuId(
                        null == update.getMenu().getParentMenu() ?
                                update.getMenu().getGuid() :
                                update.getMenu().getParentMenu().getGuid()));
            }

    }
}