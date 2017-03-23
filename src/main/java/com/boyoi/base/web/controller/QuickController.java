package com.boyoi.base.web.controller;

import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.service.QuickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 快捷方式 web
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"quick" })
public class QuickController {

    @Autowired
    private QuickService quickService;

    /**
     * 获得快捷方式内容的数量
     * @return key->数量
     */
    @ResponseBody
    @RequestMapping(value = "quickNum")
    public Map<String, String> quickNum(HttpSession session){
        User user = (User) session.getAttribute(LoginFlag.LOGIN_SUCCESS.name());
        return quickService.getQuickNum(user.getGuid());
    }

}
