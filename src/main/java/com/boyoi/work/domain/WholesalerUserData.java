package com.boyoi.work.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yishujun on 16/8/19.
 * 销售者基础信息完善
 */
public class WholesalerUserData implements Serializable {
    // 所属摊位集合
    private List<BoothType> boothList = new ArrayList<>();
    //照片
    private List<UserInfoPhoto> picList = new ArrayList<>();
    // 所属市场id
    private String wmid;
    private String marketArea;
    private String boothNo;
    private String applicant;
    private String userType;
    private String verifyId;
    // 性质
    private String nature;
    //销售者编号
    private String wholesalerId;
    //企业名称或销售者名称
    private String wholesalerName;
    // 社会信用代码
    private String socialCreditCode;
    // 主要进货渠道
    private String stockChannel;
    // 主要产地
    private String production;
    // 身份证号
    private String cardNo;
    // 法人代表
    private String legal;
    // 电话号码
    private String mobile;
    // 地址
    private String address;


    public List<BoothType> getBoothList() {
        return boothList;
    }

    public void setBoothList(List<BoothType> boothList) {
        this.boothList = boothList;
    }

    public List<UserInfoPhoto> getPicList() {
        return picList;
    }

    public void setPicList(List<UserInfoPhoto> picList) {
        this.picList = picList;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getWholesalerId() {
        return wholesalerId;
    }

    public void setWholesalerId(String wholesalerId) {
        this.wholesalerId = wholesalerId;
    }

    public String getWholesalerName() {
        return wholesalerName;
    }

    public void setWholesalerName(String wholesalerName) {
        this.wholesalerName = wholesalerName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getStockChannel() {
        return stockChannel;
    }

    public void setStockChannel(String stockChannel) {
        this.stockChannel = stockChannel;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWmid() {
        return wmid;
    }

    public void setWmid(String wmid) {
        this.wmid = wmid;
    }

    public String getMarketArea() {
        return marketArea;
    }

    public void setMarketArea(String marketArea) {
        this.marketArea = marketArea;
    }

    public String getBoothNo() {
        return boothNo;
    }

    public void setBoothNo(String boothNo) {
        this.boothNo = boothNo;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }
}
