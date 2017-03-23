package com.boyoi.base.domain;

/**
 * android,ios token自定义实现
 * @author Chenj
 */
public class Token {

    /**
     * 令牌
     */
    private String access_token;

    /**
     * 更新令牌的token
     */
    private String refresh_token;

    /**
     * 过期时间
     */
    private long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
}
