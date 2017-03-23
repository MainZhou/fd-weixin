package com.boyoi.core.web.controller;

import com.boyoi.base.service.MenuService;
import com.boyoi.core.creator.InputTypeEnum;
import com.boyoi.core.domain.ColumnDb;
import com.boyoi.core.domain.ColumnRequest;
import com.boyoi.core.service.CreatorService;
import com.boyoi.core.utils.PdUtil;
import com.boyoi.core.utils.PropertiesUtil;
import com.boyoi.core.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * 代码自动生成工具(jsp,js,dao,service,controller)
 * @author Chenj
 */
@Controller
@RequestMapping(value = "creator")
public class CreatorController {

    @Autowired
    private CreatorService creatorService;

    @Autowired
    private MenuService menuService;

    /**
     * 步骤1.查找出所有的tables.
     * @return 视图
     */
    @RequestMapping(value = "step1", method = RequestMethod.GET)
    public String step1(){
        return "core/creator/step1";
    }

    /**
     * 步骤1.查找出所有的tables.
     * @return JSON
     */
    @RequestMapping(value = "step1", method = RequestMethod.POST)
    @ResponseBody
    public Collection<String> getAllTables(){
        return creatorService.getTables();
    }

    /**
     * 步骤2.根据table加载字段
     * @return 视图
     */
    @RequestMapping(value = "step2", method = RequestMethod.GET)
    public String step2(String tableName, Model model){
        tableName = tableName.trim();

        model.addAttribute("tableName",tableName);
        model.addAttribute("databaseName",creatorService.getDatabaseName());

        // 查找一级菜单
        model.addAttribute("level1Menu",menuService.findLevel1Menu());

        //处理表名.获得前缀名
        String prefix = StringUtil.tableName2Prefix(tableName);
        model.addAttribute("prefix",prefix);

        //从creator.properties文件读取配置
        PropertiesUtil propUtil =  PdUtil.propUtil;
        model.addAttribute("absPath",propUtil.readString("absPath"));//绝对路径
        model.addAttribute("packageName",propUtil.readString("packageName"));//包名

        //判断pd文件是否存在
        File file = new File(propUtil.readString("pdFilePath"));
        if (file.exists())
            model.addAttribute("pdPath",file.getPath());

        return "core/creator/step2";
    }

    /**
     * 步骤2.根据table加载字段,并从pd中读取字段的中文名
     * @return JSON
     */
    @RequestMapping(value = "step2", method = RequestMethod.POST)
    @ResponseBody
    public Collection<ColumnDb> getColumnByTableName(String tableName){
        return  creatorService.getColumnByTable(tableName.trim());
    }

    /**
     * 步骤2.获得输入类型JSON
     * @return JSON
     */
    @RequestMapping(value = "inputType", method = RequestMethod.POST)
    @ResponseBody
    public InputTypeEnum[] getInputType(){
        return InputTypeEnum.values();
    }

    /**
     * 步骤3.获得提交的字段属性,并处理
     *
     * @param columnRequest 接收提交的ColumnRequest
     * @param servletRequest HttpServletRequest
     * @return 视图
     */
    @RequestMapping(value = "step3", method = RequestMethod.POST)
    public String step3(ColumnRequest columnRequest, HttpServletRequest servletRequest, Model model){
        Map<Boolean,Collection<String>> msg = creatorService.execColumnRequest(columnRequest, servletRequest);
        model.addAttribute("msg", msg);
        return "core/creator/step3";
    }


}
