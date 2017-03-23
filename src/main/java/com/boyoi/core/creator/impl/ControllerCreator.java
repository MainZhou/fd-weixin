package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 生成控制层的实现类
 * @author Chenj
 */
public class ControllerCreator extends CreatorHelper {

    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public ControllerCreator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return javaPath + File.separator +
                columnRequest.getPackageName().replace(".", File.separator) + File.separator +
                "web"+ File.separator +
                "controller"+ File.separator +
                columnRequest.getPrefix() + "Controller.java";
    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/controller";
    }
}
