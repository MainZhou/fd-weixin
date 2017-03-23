package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 生成javabean 的实现类
 * @author Chenj
 */
public class JavaBeanCreator extends CreatorHelper {

    public JavaBeanCreator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return javaPath + File.separator +
               columnRequest.getPackageName().replace(".", File.separator) + File.separator +
               "domain"+ File.separator +
               columnRequest.getPrefix() + ".java";
    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/javaBean";
    }

}
