package com.boyoi.core.exception;

import com.boyoi.core.enums.CommonExceptionEnum;

/**
 * 异常的基类。继承此类。
 * spring MVC返回子类对应错误码及错误信息
 * @author Chenj
 */
public class CommonException extends RuntimeException{

    private CommonExceptionEnum commonExceptionEnum;

    private CommonException() {
        super();
    }

    public CommonException(CommonExceptionEnum commonExceptionEnum){
        super("[错误码："+ commonExceptionEnum.getErrorCode()+"]"+ commonExceptionEnum.getErrorMsg());
        this.commonExceptionEnum = commonExceptionEnum;
    }

    public CommonExceptionEnum getCommonExceptionEnum() {
        return commonExceptionEnum;
    }

}
