package com.boyoi.core.web.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.boyoi.core.enums.CommonExceptionEnum;
import com.boyoi.core.exception.CommonException;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.work.utils.WxSessionRefreshKeyUtil;
import com.boyoi.work.utils.WxUserInfoSaveUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义全局异常处理器
 * 返回JSON对象的异常信息
 * @author Chenj
 */
public class JsonHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandlerExceptionResolver.class);

    @Autowired
    private WxUserInfoSaveUtil wxUserInfoSaveUtil;

    @Autowired
    private WxSessionRefreshKeyUtil wxSessionRefreshKeyUtil;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
        fastJsonJsonView.setContentType("application/json;charset=UTF-8");

        Map<String, Object> para = new HashMap<>();
        para.put("status", false);
        para.put("msg", "系统发生异常！请联系管理员！");

        if (ex instanceof CommonException){
            CommonExceptionEnum commonExceptionEnum = ((CommonException) ex).getCommonExceptionEnum();
            para.put("msg", commonExceptionEnum.getErrorMsg() + "！错误码："+commonExceptionEnum.getErrorCode());
            if (commonExceptionEnum == CommonExceptionEnum.E1 || commonExceptionEnum == CommonExceptionEnum.E2){
                try {

                    // 删除旧的cooike
                    Cookie[] cookies = request.getCookies();
                    if (null != cookies){
                        for (Cookie cookie : cookies) {
                            if ("t".equals(cookie.getName())) {
                                cookie.setMaxAge(0);
                                cookie.setPath("/");
                                cookie.setHttpOnly(true);
                                response.addCookie(cookie);
                                // 删除对象中的
                                wxUserInfoSaveUtil.del(cookie.getValue());
                                wxSessionRefreshKeyUtil.del(cookie.getValue());
                            }
                        }
                    }

                    // 删除cookie
                    request.setAttribute("error", "登录超时！请重新登录！");
                    request.getRequestDispatcher("/wxLogin").forward(request, response);

                } catch (ServletException|IOException e) {
                    e.printStackTrace();
                }
            }
        }

        fastJsonJsonView.setAttributesMap(para);

        if (ex.getStackTrace().length>0){
            LOGGER.error("系统自动捕获到了异常! 请检查! ClassName:{}, lineNumber:{}, Msg:{}",
                    ex.getStackTrace()[0].getClassName(),
                    ex.getStackTrace()[0].getLineNumber(),
                    ex.getMessage());
        }else {
            LOGGER.error("系统自动捕获到了异常! 请检查! Msg:{}",
                    ex.getMessage());
        }

        ex.printStackTrace();
        // 返回View
        ModelAndView mav = new ModelAndView();
        mav.setView(fastJsonJsonView);
        return mav;
    }
}
