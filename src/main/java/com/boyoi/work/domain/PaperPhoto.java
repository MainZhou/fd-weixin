package com.boyoi.work.domain;

/**
 * 销售拍照图片对象
 * User: zhoujl
 * Date: 2017/2/8
 * Time: 13:30
 */
public class PaperPhoto {
    /**
     * 销售时间
     */
    private String stockTime;
    /**
     * 图片路径
     */
    private String path;

    public PaperPhoto() {
    }

    public void setStockTime(String stockTime) {

        this.stockTime = stockTime;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStockTime() {

        return stockTime;
    }

    public String getPath() {
        return path;
    }
}
