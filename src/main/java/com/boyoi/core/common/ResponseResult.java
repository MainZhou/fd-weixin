package com.boyoi.core.common;

import java.util.Map;

/**
 * 返回结果
 *
 * @author Chenj
 */
public class ResponseResult {
    /**
     * 状态,true成功.false失败
     */
    private boolean status = false;
    /**
     * 详细信息
     */
    private String msg = "系统错误!请稍后在试!";
    /**
     * 返回结果集
     */
    private Map<String, Object> resultMap;

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
