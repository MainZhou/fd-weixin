package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * @author Chenj
 */
public class DaoCreator extends CreatorHelper {

    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public DaoCreator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return javaPath + File.separator +
                columnRequest.getPackageName().replace(".", File.separator) + File.separator +
                "dao"+ File.separator +
                columnRequest.getPrefix() + "Dao.java";
    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/dao";
    }
}
