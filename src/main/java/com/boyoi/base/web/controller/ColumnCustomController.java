package com.boyoi.base.web.controller;

import com.boyoi.base.domain.ColumnCustom;
import com.boyoi.base.service.ColumnCustomService;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 列配置 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = "columnCustom" )
public class ColumnCustomController extends BaseController<ColumnCustom,ColumnCustomService>{

    @Autowired
    private ColumnCustomService service;

    /**
    * 添加或更新列配置
    * @param columnCustom 实体
    * @param bindingResult 服务段校验,返回的结果
    */
    @RequestMapping(value = "addOrUpdate", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult addOrUpdate(@Validated ColumnCustom columnCustom, BindingResult bindingResult){

        //判断是否有错,有错返回添加页面
        if(bindingResult.hasErrors()){
            execErrors(bindingResult);
            LOG.debug("添加或更新列配置失败! 没通过服务端校验!");
            return responseResult;
        }

        // 设置UserId
        columnCustom.setUserId(super.getUser().getGuid());
        // 修改或添加
        service.addOrUpdate(columnCustom);
        responseResult.setStatus(true);
        responseResult.setMsg("添加成功!");
        return responseResult;
    }

    @RequestMapping(value = "findCurrCustomColumn", method=RequestMethod.POST)
    @ResponseBody
    public Object findCurrCustomColumn(String domainUrl){
        if (null != domainUrl && !"".equals(domainUrl.trim())){
            ColumnCustom currColumnCustom = service.findCurrColumnCustom(getUser().getGuid(), domainUrl);
            if (null != currColumnCustom){
                return currColumnCustom;
            }else {
                responseResult.setStatus(true);
                responseResult.setMsg("当前grid没有自定义配置");
                return responseResult;
            }
        }
        responseResult.setStatus(false);
        responseResult.setMsg("domainUrl不能为空！");
        return responseResult;
    }

    @RequestMapping(value = "del", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult del(String domainUrl){
        if (null != domainUrl && !"".equals(domainUrl.trim())){
            int isSuccess = service.del(getUser().getGuid(), domainUrl);
            if (isSuccess==0){
                responseResult.setStatus(true);
                responseResult.setMsg("当前grid没有自定义配置！");
            }else {
                responseResult.setStatus(true);
                responseResult.setMsg("清除自定列成功！");
            }
        }else {
            responseResult.setStatus(false);
            responseResult.setMsg("domainUrl不能为空！");
        }
        return responseResult;
    }


}