package com.boyoi.core.web.servlet;

import com.boyoi.base.enums.LoginFlag;
import com.boyoi.core.utils.ValidateCodeCreator;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 随机图片验证码调用的Servlet
 * <pre>
 * 在页面上只需要直接调用该Servlet即可
 * 页面上使用方法：
 * <img id="myValidateImage" src="${ctx}/validatecode"/>
 * 	配置格式：
 * 	<servlet>
 * 	    <servlet-name>ValidateCode</servlet-name>
 * 	    <servlet-class>com.boyoi.core.web.servlet.ValidateCodeServlet</servlet-class>
 * 	</servlet>
 * 	<servlet-mapping>
 * 	    <servlet-name>ValidateCode</servlet-name>
 * 	    <url-pattern>/validatecode</url-pattern>
 * 	</servlet-mapping>
 * </pre>
 * @author AllenZhang
 * @version 0.1 (2008.11.06)
 * @modify AllenZhang (2008.11.06)
 */
public class ValidateCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -5008620428872558929L;

	/**
	 * 处理 get 请求
	 * @param request http 请求
	 * @param response http 响应
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @author AllenZhang
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
		/**
		 * 页面属性设置
		 */
        response.setContentType("image/jpeg");    
        response.setHeader("Pragma", "No-cache");    
        response.setHeader("Cache-Control", "no-cache");    
        response.setDateHeader("Expires", 0);
        
        try {    
        	/**
        	 * 生成图象与验证码
        	 */
            ValidateCodeCreator validateCodeCreator = new ValidateCodeCreator();
            BufferedImage img = validateCodeCreator.creatImageAndCode();

            request.getSession().setAttribute(LoginFlag.VALIDATE_CODE.name(), validateCodeCreator.getValidateCode());
            
            ImageIO.write(img, "JPEG", response.getOutputStream());
            response.getOutputStream().flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            response.getOutputStream().close();
        }
    }    
}