package com.boyoi.base.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 *
 * @author Chenj
 */
@Controller
public class IndexController{

    /**
     * 首页界面，跳转登录
     */
    @RequestMapping(value = "/")
    public String login(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (null != cookies){
            for (Cookie cookie : cookies){
                if ("t".equals(cookie.getName())){
                    return "redirect:/wxAdmin";
                }
            }
        }
        return "redirect:/wxLogin";
    }

}
