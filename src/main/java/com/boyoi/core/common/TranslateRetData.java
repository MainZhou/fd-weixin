package com.boyoi.core.common;

/**
 * 调用百度翻译
 * 返回的json数据的retData
 * @author Chenj
 */
public class TranslateRetData {
    private String from;
    private String to;
    private TransResult[] trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TransResult[] getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(TransResult[] trans_result) {
        this.trans_result = trans_result;
    }
}
