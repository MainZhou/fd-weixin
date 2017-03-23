package com.boyoi.work.domain;

import java.io.Serializable;

/**
 * Created by yishujun on 16/8/20.
 */
public class BoothType implements Serializable {
    //品种编码
    private String boothCode;

    public BoothType(String boothCode) {
        this.boothCode = boothCode;
    }
    public BoothType() {

    }

    public String getBoothCode() {
        return boothCode;
    }

    public void setBoothCode(String boothCode) {
        this.boothCode = boothCode;
    }
}
