package com.boyoi.base.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security
 * 登录失败处理器
 * 从session取值,如用户登录失败超过三次,要求输入验证码
 * @author Chenj
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        //保存登录失败信息
        saveException(request, exception);

        //保存登录失败次数到LoginFailHelper帮助类中
        String remoteAddr = request.getRemoteAddr();
        LoginFailHelper.addOneTimes(remoteAddr);

        log.info("用户:{},登录失败!登录IP:{}", request.getParameter("login_name"), remoteAddr);

        // 重新跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/user/login");

    }


}
