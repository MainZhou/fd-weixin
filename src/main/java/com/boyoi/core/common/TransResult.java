package com.boyoi.core.common;

/**
 * 调用百度翻译
 * 返回的json数据的trans_result
 * @author Chenj
 */
public class TransResult {
    private String src;
    private String dst;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
