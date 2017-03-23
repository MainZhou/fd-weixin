package com.boyoi.core.common;

/**
 * 调用百度翻译http://apistore.baidu.com/microservice/translate?query=hello&from=en&to=zh
 * 返回的json数据
 *
         {
         "errNum": 0,
         "errMsg": "success",
         "retData": {
         "from": "en",
         "to": "auto",
         "trans_result": [
         {
         "src": "hello",
         "dst": "您好"
         }
         ]
         }
         }
 * @author Chenj
 */
public class TranslateJson {
    private Integer errNum;
    private String errMsg;
    private TranslateRetData retData;

    public Integer getErrNum() {
        return errNum;
    }

    public void setErrNum(Integer errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public TranslateRetData getRetData() {
        return retData;
    }

    public void setRetData(TranslateRetData retData) {
        this.retData = retData;
    }
}

