package com.boyoi.work.domain;

import java.util.List;

/**
 * 进货明细
 * User: zhoujl
 * Date: 2017/1/16
 * Time: 11:12
 */
public class MakeupInfo {
    /**
     * 明细编号
     */
    private String detailId;
    /**
     * 种类编号
     */
    private String typeCode;
    /**
     * 种类名称
     */
    private String typeName;
    /**
     * 单品重量(kg)
     */
    private String weight;
    /**
     * 单价
     */
    private String price;
    /**
     * 补录时间
     */
    private String stockTime;
    /**
     * 建档时间
     */
    private String createTime;
    /**
     * 中文地址
     */
    private String sourcePlace;
    /**
     * 地址编号
     */
    private String sourcePlaceId;
    /**
     * 乡镇/村社区/基地/农户
     */
    private String sourcePlaceUnit;
    /**
     * 证照数据
     */
    private List<MakeupPhoto> photoData;

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setSourcePlace(String sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

    public void setSourcePlaceId(String sourcePlaceId) {
        this.sourcePlaceId = sourcePlaceId;
    }

    public void setSourcePlaceUnit(String sourcePlaceUnit) {
        this.sourcePlaceUnit = sourcePlaceUnit;
    }

    public void setPhotoData(List<MakeupPhoto> photoData) {
        this.photoData = photoData;
    }

    public String getTypeCode() {

        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
        return price;
    }

    public String getStockTime() {
        return stockTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getSourcePlace() {
        return sourcePlace;
    }

    public String getSourcePlaceId() {
        return sourcePlaceId;
    }

    public String getSourcePlaceUnit() {
        return sourcePlaceUnit;
    }

    public List<MakeupPhoto> getPhotoData() {
        return photoData;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDetailId() {

        return detailId;
    }
}
