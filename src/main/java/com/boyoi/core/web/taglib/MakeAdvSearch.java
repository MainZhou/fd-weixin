package com.boyoi.core.web.taglib;

import com.boyoi.core.utils.GetI18N;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 生成高级搜索html代码的标签库
 * @author Chenj
 */
public class MakeAdvSearch extends SimpleTagSupport{

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        pageContext.getOut().write("<a href=\"javascript:void(-1)\" id=\"advSearchBtn\" class=\"btn btn-link\"" +
                "title=\""
                +GetI18N.get(request, "global.advSearch")
                +"\"><span class=\"fa fw fa-search-plus\"></span></a>");
    }

}
