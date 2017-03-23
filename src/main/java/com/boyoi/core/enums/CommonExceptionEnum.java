package com.boyoi.core.enums;

/**
 * 自定义异常枚举
 * @author Chenj
 */
public enum CommonExceptionEnum {
    E0(0, "正常"),
    E1(1, "token过期"),
    E2(2, "cookie找不到"),
    E3(3, "没有权限");

    private int errorCode;
    private String errorMsg;

    private CommonExceptionEnum(int errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg  = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
