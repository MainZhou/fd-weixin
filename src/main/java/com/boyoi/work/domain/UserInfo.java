package com.boyoi.work.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjl
 * Date: 2016/11/28
 * Time: 13:26
 */
public class UserInfo {
    /**
     * 姓名
     */
    private String managerPerson;
    /**
     * 身份证
     */
    private String managerCardNo;
    /**
     * 区域Id
     */
    private String areaId;
    /**
     * 手机号
     */
    private String managerTelephone;
    /**
     * 所属市场
     */
    private String parentId;
    /**
     * 注册类型
     */
    private String regType;
    /**
     * 摊位号
     */
    private String boothNo;
    /**
     * 经营品种集合
     */
    private List<BoothType> boothInfo = new ArrayList<>();
    /**
     * 企业简称
     */
    private String companyName;
    /**
     * 企业全称
     */
    private String companyWholeName;
    /**
     * 企业类别
     */
    private String companyType;

    /**
     * 企业地址
     */
    private String address;
    /**
     * 负责人姓名
     */
    private String chargePerson;
    /**
     * 负责人手机
     */
    private String mobile;
    /**
     * 抽样者姓名
     */
    private String samplePerson;
    /**
     * 检验者姓名
     */
    private String detecter;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyWholeName(String companyWholeName) {
        this.companyWholeName = companyWholeName;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSamplePerson(String samplePerson) {
        this.samplePerson = samplePerson;
    }

    public void setDetecter(String detecter) {
        this.detecter = detecter;
    }

    public String getDetecter() {

        return detecter;
    }

    public String getSamplePerson() {
        return samplePerson;
    }

    public String getMobile() {
        return mobile;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getCompanyWholeName() {
        return companyWholeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setManagerPerson(String managerPerson) {
        this.managerPerson = managerPerson;
    }

    public void setManagerCardNo(String managerCardNo) {
        this.managerCardNo = managerCardNo;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setManagerTelephone(String managerTelephone) {
        this.managerTelephone = managerTelephone;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public void setBoothNo(String boothNo) {
        this.boothNo = boothNo;
    }

    public void setBoothInfo(List<BoothType> boothInfo) {
        this.boothInfo = boothInfo;
    }

    public List<BoothType> getBoothInfo() {

        return boothInfo;
    }

    public String getManagerPerson() {

        return managerPerson;
    }

    public String getManagerCardNo() {
        return managerCardNo;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getManagerTelephone() {
        return managerTelephone;
    }

    public String getParentId() {
        return parentId;
    }

    public String getRegType() {
        return regType;
    }

    public String getBoothNo() {
        return boothNo;
    }

}
