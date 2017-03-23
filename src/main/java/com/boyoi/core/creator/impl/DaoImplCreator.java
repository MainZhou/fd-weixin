package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 生成 MyBatis sql文件(xml)的实现类
 *
 * @author Chenj
 */
public class DaoImplCreator extends CreatorHelper {

    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public DaoImplCreator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return javaPath + File.separator +
                columnRequest.getPackageName().replace(".", File.separator) + File.separator +
                "dao"+ File.separator +
                "impl"+ File.separator +
                columnRequest.getPrefix() + "-map.xml";    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/daoImpl";
    }
}
