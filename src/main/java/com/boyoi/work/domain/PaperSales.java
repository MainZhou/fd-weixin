package com.boyoi.work.domain;

import java.util.List;

/**
 * 销售拍照对象
 * User: zhoujl
 * Date: 2017/2/8
 * Time: 13:32
 */
public class PaperSales {
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 销售商Id
     */
    private String wholesalerId;
    /**
     * 建档时间
     */
    private String stockTime;
    /**
     * 照片集合
     */
    private List<PaperPhoto> data;

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setWholesalerId(String wholesalerId) {
        this.wholesalerId = wholesalerId;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public void setData(List<PaperPhoto> data) {
        this.data = data;
    }

    public String getUserType() {

        return userType;
    }

    public String getWholesalerId() {
        return wholesalerId;
    }

    public String getStockTime() {
        return stockTime;
    }

    public List<PaperPhoto> getData() {
        return data;
    }

    public PaperSales() {

    }
}
