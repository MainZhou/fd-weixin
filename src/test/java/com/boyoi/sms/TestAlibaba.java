package com.boyoi.sms;

import com.boyoi.work.utils.GenSmsCode;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.junit.Test;

/**
 * @author Chenj
 */
public class TestAlibaba {
    @Test
    public void test() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23467978", "0437844b31901863d29f9cf916b4f99a");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName("小陈");
        req.setSmsParamString("{number:'2222'}");
        req.setRecNum("13730729977");
        req.setSmsTemplateCode("SMS_16505287");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }

    @Test
    public void random(){
        System.out.println(GenSmsCode.random4Number());

    }
}
