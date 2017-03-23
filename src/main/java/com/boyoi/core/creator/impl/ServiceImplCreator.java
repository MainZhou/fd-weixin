package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 生成service层实现类 的实现方法
 * @author Chenj
 */
public class ServiceImplCreator extends CreatorHelper {

    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public ServiceImplCreator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return javaPath + File.separator +
                columnRequest.getPackageName().replace(".", File.separator) + File.separator +
                "service"+ File.separator +
                "impl"+ File.separator +
                columnRequest.getPrefix() + "ServiceImpl.java";
    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/serviceImpl";
    }
}
