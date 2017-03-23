package com.boyoi.work.domain;

import java.util.List;
import java.util.Map;

/**
 * 进货主记录
 * User: zhoujl
 * Date: 2017/1/16
 * Time: 11:25
 */
public class Makeup {
    /**
     * 销售者Id
     */
    private String wholesalerId;
    /**
     * 市场Id
     */
    private String wmId;
    /**
     * 补录总价
     */
    private String totalPrice;
    /**
     * 补录总重
     */
    private String totalWeight;
    /**
     * 进货时间
     */
    private String stockTime;
    /**
     * 建档时间
     */
    private String createTime;
    /**
     * 进货明细
     */
    private List<MakeupInfo> data;


    public void setWmId(String wmId) {
        this.wmId = wmId;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setWholesalerId(String wholesalerId) {
        this.wholesalerId = wholesalerId;
    }

    public String getWholesalerId() {

        return wholesalerId;
    }

    public String getWmId() {
        return wmId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public String getStockTime() {
        return stockTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setData(List<MakeupInfo> data) {
        this.data = data;
    }

    public List<MakeupInfo> getData() {

        return data;
    }
}
