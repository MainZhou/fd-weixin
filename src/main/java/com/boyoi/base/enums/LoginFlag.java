package com.boyoi.base.enums;

/**
 * 登录成功,在session做的标记
 * @author Chenj
 */
public enum LoginFlag {
    LOGIN_SUCCESS,//登录成功
    LOGIN_FAIL,//登录失败
    VALIDATE_CODE,//验证码
    LOGIN_ERROR_TIME,//登录失败次数
    USER_MENU, // 用户对应菜单
    USER_LOGIN_LOG // 用户登录日志
}