package com.boyoi.core.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 自定义标签库
 * 传一串字符,和最大长度
 * 返回从0到最大长度的字符
 *
 * @author Chenj
 */
public class SubLength extends SimpleTagSupport {
    //传入的一串字符
    private String content;
    //传入的最大长度
    private Integer maxLen;
    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        int contentLen = content.length();
        //当前需要的大小
        int currLen = 0;
        //长度小于或等于最大长度,不改就写入
        if (contentLen <= maxLen){
            pageContext.getOut().write(content);
        }else {
            //以标签传来的最大长度为最大值,中文+1,英文及数字+2,并加上省略号
            //循环content为char,判断是否为单词字符[a-zA-Z_0-9],是的话,长度+1
            Pattern pattern = Pattern.compile("\\w");
            for (int i = 0; i< maxLen; i++){
                Character str = content.charAt(i);
                if(pattern.matcher(str.toString()).matches())
                    currLen += 2;  // 英文就多加一个英问
                else
                    currLen += 1;  // 中文就只占一个
            }

            //防止最大
            if (contentLen < currLen)
                currLen = contentLen;

            pageContext.getOut().write(content.substring(0, currLen - 3));
            pageContext.getOut().write("...");

        }
        super.doTag();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(Integer maxLen) {
        this.maxLen = maxLen;
    }
}
