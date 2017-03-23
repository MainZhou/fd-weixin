package com.boyoi.core.web.controller;

import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.core.common.RemoteValidatorResult;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Controller层的基类
 * @param <T> 实体
 * @param <S> service
 * @author Chenj
 */
public abstract class BaseController<T extends BaseDomain, S extends BaseService> {

    //把传来的继续baseservice的service实例化
	@Autowired
	protected S service;

    protected Logger LOG;

    //自动注入request,继承本类的子类就可以直接使用
    @Autowired
    protected HttpServletRequest request;

    //自动注入session,继承本类的子类就可以直接使用
    @Autowired
    protected HttpSession session;

    protected ResponseResult responseResult = new ResponseResult();

    @SuppressWarnings("unchecked")
    public BaseController(){
        /**
         * ParameterizedType就是范型,
         * getActualTypeArguments()[0]获得调用者的泛型中的值，
         */
        ParameterizedType classt = (ParameterizedType) this.getClass().getGenericSuperclass();	//返回调用者的泛型中的Class对象
        LOG = LoggerFactory.getLogger((Class<T>) classt.getActualTypeArguments()[0]);
    }


    /**
     * 校验实体中是否存在对应的数据
     * @param t 实体
     * @return json
     */
    @RequestMapping(value = "check")
    @ResponseBody
    public RemoteValidatorResult check(T t){
        RemoteValidatorResult validatorResult = new RemoteValidatorResult();
        if (service.findByDomain(t).size() > 0)
            validatorResult.setError("此项已经存在,请重新输入!");
        else
            validatorResult.setOk("√");

        return validatorResult;
    }

    /**
     * 处理包含有验证错误
     * @param result BindingResult
     */
    protected void execErrors(BindingResult result){
        List<ObjectError> allErrors = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError: allErrors){
            sb.append(objectError.getDefaultMessage());
        }
        responseResult.setStatus(false);
        responseResult.setMsg(sb.toString());
    }

    /**
     * 获得当前登录用户
     * @return  用户对象
     */
    protected User getUser(){
        return (User) session.getAttribute(LoginFlag.LOGIN_SUCCESS.name());
    }

}
