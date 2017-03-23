package com.boyoi.work.web.servlet;

import com.boyoi.core.utils.PropertiesUtil;
import com.boyoi.work.utils.WxUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 初始化微信相关
 * @author Chenj
 */
@WebServlet(loadOnStartup = 1,urlPatterns = "/initBaseUrl")
public class InitBase extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        PropertiesUtil propertiesUtil = new PropertiesUtil("/weixin.properties");
        String baseUrl = propertiesUtil.readString("baseUrl", "http://localhost/");
        // 添加到全局域中
        getServletContext().setAttribute("baseUrl", baseUrl);
        // 添加到工具类中
        WxUtils.setBaseUrl(baseUrl);
    }
}
