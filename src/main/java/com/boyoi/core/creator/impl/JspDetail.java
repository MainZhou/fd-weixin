package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 生成 详情页面的JSP 的实现类
 * @author Chenj
 */
public class JspDetail extends CreatorHelper {

    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public JspDetail(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return webappPath + File.separator +
                "WEB-INF" + File.separator +
                "page" + File.separator +
                columnRequest.getPrefix() + File.separator +
                "detail.jsp";

    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/jspDetail";
    }
}
