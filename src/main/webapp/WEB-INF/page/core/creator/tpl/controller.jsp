package ${columnRequest.packageName}.web.controller;
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import ${columnRequest.packageName}.domain.${columnRequest.prefix};
import ${columnRequest.packageName}.service.${columnRequest.prefix}Service;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * ${columnRequest.funcName} controller层
 *
 * @author ${columnRequest.devPerson}
 */
@Controller
@RequestMapping(value = "${columnRequest.prefixFirstCharLow}" )
public class ${columnRequest.prefix}Controller extends BaseController<${columnRequest.prefix},${columnRequest.prefix}Service>{

    @Autowired
    private ${columnRequest.prefix}Service service;

    /**
    * 列表
    */
    @RequestMapping(value="list", method=RequestMethod.GET)
    public String list(){
        return "${columnRequest.prefix}/list";
    }

    /**
    * 根据EasyGridRequest请求,例出实体
    */
    @RequestMapping(value="list", method=RequestMethod.POST)
    @ResponseBody
    public EasyUiPage.Page listByGridRequest(EasyGridRequest gridRequest){
        return service.findByGridRequest(gridRequest);
    }

    /**
     * 添加界面
     */
    @RequestMapping(value = "add", method=RequestMethod.GET)
    public String addUi(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}){
        return "${columnRequest.prefix}/add";
    }

    /**
    * 添加功能
    * @param ${columnRequest.prefixFirstCharLow} 实体
    * @param bindingResult 服务段校验,返回的结果
    */
    @RequestMapping(value = "add", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(@Validated ${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}, BindingResult bindingResult){

        //判断是否有错,有错返回添加页面
        if(bindingResult.hasErrors()){
            execErrors(bindingResult);
            LOG.debug("添加数据失败! 没通过服务端校验!");
            return responseResult;
        }

        int result = service.add(${columnRequest.prefixFirstCharLow});
        responseResult.setStatus(result == 1);
        responseResult.setMsg("添加成功!");
        return responseResult;
    }

    /**
    * 更新实体的界面
    * @param guid 实体的id
    */
    @RequestMapping(value="update", method = RequestMethod.GET)
    public String updateUI(String guid, Model model){
        // 存放在model, 用于展示页面
        model.addAttribute("update", service.findByGuid(guid));
        return "${columnRequest.prefix}/update";
    }

    /**
    * 更新实体的功能
    * @param ${columnRequest.prefixFirstCharLow} 实体
    * @param bindingResult 服务段校验,返回的结果
    */
    @RequestMapping(value="update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@Validated ${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}, BindingResult bindingResult, Model model){

        //有错,返回更新界面
        if (bindingResult.hasErrors()){
            execErrors(bindingResult);
            return responseResult;
        }

        try {
            service.update(${columnRequest.prefixFirstCharLow});
        }catch (Exception e){
            e.printStackTrace();
            responseResult.setStatus(false);
            responseResult.setMsg("更新失败!");
            return responseResult;
        }

        responseResult.setStatus(true);
        responseResult.setMsg("更新成功!");
        return responseResult;

    }

    /**
    * 删除实体（批量）
    * @param ids 待删除的id数组
    */
    @RequestMapping(value="del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delByArray(String[] ids){
        int successNum = 0;
        boolean reference = false;

        try {
            successNum = service.delBatch(Arrays.asList(ids));
        }catch (DataIntegrityViolationException e){
            reference = true;
        }

        if (reference){
            responseResult.setStatus(false);
            responseResult.setMsg("有值引用,有部份数据没有成功删除! 共成功删除" + successNum + "条数据! ");
        }else {
            responseResult.setStatus(true);
            responseResult.setMsg("成功删除" + successNum + "条数据! ");
        }

        return responseResult;
    }

    /**
    * 详情
    * @param guid guid
    */
    @RequestMapping(value="detail")
    public String detail(String guid, Model model){
        ${columnRequest.prefix} ${columnRequest.prefixFirstCharLow} = service.findByGuid(guid);
        model.addAttribute("detail", ${columnRequest.prefixFirstCharLow});
        return "${columnRequest.prefix}/detail";
    }

}