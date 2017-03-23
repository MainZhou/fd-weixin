package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Icon;
import com.boyoi.base.service.IconService;
import com.boyoi.core.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图标 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"icon", "icon" })
public class IconController extends BaseCrudController<Icon,IconService> {

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param icon 图标实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(Icon icon, Model model) {

    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {

    }
}