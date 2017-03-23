package com.boyoi.core.web.taglib;

import com.boyoi.core.utils.GetI18N;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 生成普通搜索html代码的标签库
 * @author Chenj
 */
public class MakeSearch extends SimpleTagSupport{
    //传入的搜索表中的名称
    private String name;
    //传入的placeholder
    private boolean addPlease = false;
    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        StringWriter sw = new StringWriter();

        // 是否添加请输入
        if (addPlease){
            sw.write(GetI18N.get((HttpServletRequest)pageContext.getRequest(), "global.please.input"));
        }
        getJspBody().invoke(sw);
        String html = "<div class=\"input-group\">\n<input id=\"normalSearch\" type=\"text\" name=\"" + name + "\" class=\"form-control\" placeholder=\"" + sw.toString().trim() + "\">\n<span class=\"input-group-btn\"><button id=\"normalSearchBtn\" class=\"btn btn-default\" type=\"submit\"><span class=\"glyphicon glyphicon-search\"></span></button></span>\n</div>";
        pageContext.getOut().write(html);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAddPlease() {
        return addPlease;
    }

    public void setAddPlease(boolean addPlease) {
        this.addPlease = addPlease;
    }
}
