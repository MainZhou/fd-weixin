package com.boyoi.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * 生成高级搜索html代码的标签库
 * @author Chenj
 */
public class MakeAdd extends SimpleTagSupport{

    @SuppressWarnings("unused")
	@Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        pageContext.getOut().write("<a class=\"add-tag\">\n" +
                "                        <div class=\"addBtu\">\n" +
                "                        <i class=\"fa fa-fw fa-plus\" style=\"position: absolute;left: 8px;top: 9px;\"></i>\n" +
                "                        <span style=\"position: absolute;left: 30px;top: 0;\">添加记录</span>\n" +
                "                        </div>\n" +
                "                    </a>");
    }

}
