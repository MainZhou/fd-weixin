package com.boyoi.core.common;

/**
 * nice Validator ajax远程校验.
 *
 * @author Chenj
 */
public class RemoteValidatorResult {

    /**
     * 校验通过
     */
    private String ok;

    /**
     * 校验失败
     */
    private String error;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
