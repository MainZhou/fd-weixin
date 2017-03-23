package com.boyoi.core.web.controller;

import com.boyoi.core.domain.ColumnRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 代码自动生成工具
 * 根据模板jsp获得生成后的文件
 * @author Chenj
 */
@Controller
@RequestMapping(value = "creator")
public class CreatorTplController {

    /**
     * 获得根据不同的type.转到不同的视图上
     * 如:javaBean,controller,service,serviceImpl,dao,daoMap
     * @return 视图
     */
    @RequestMapping(value = "tpl/{type}", method = RequestMethod.POST)
    public String javaBean(@PathVariable String type, @RequestBody ColumnRequest request, Model model){
        model.addAttribute("columnRequest",request);
        return "core/creator/tpl/" + type;
    }


}
