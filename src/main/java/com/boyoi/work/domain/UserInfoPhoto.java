package com.boyoi.work.domain;

import java.io.Serializable;

/**
 * Created by yishujun on 16/8/19.
 */
public class UserInfoPhoto implements Serializable {
    //拍照名称
    private String picName;
    //拍照地址
    private String photoType;

    private String picUrl;


    public UserInfoPhoto(String picName, String photoType) {
        this.picName = picName;
        this.photoType = photoType;
    }

    public UserInfoPhoto(String photoType) {
        this.photoType = photoType;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
