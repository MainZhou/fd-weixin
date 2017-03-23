package com.boyoi.work.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.core.utils.PropertiesUtil;
import com.boyoi.work.utils.FileUtils;
import com.boyoi.work.utils.Sign;
import com.boyoi.work.utils.WxUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 消费者查询
 *
 * @author zhouJL
 */
@Controller
@RequestMapping(value = "scanCode")
public class scanCodeController {


    protected Logger LOG;

    @RequestMapping(value = "scanCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult scanCode(String wholesalerId) {
        ResponseResult responseResult = new ResponseResult();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("wholesalerId", wholesalerId);
        //  接口地址：http://192.168.1.20:8080/fd-web/purchaseRecords/fd_wholesale_entrRecord
        String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_wholesale_entrRecord";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        String result = HttpUtils.postWithJson(entrRecordUrl, jsonStr);
        JSONObject jsonObject = JSON.parseObject(result);

        if (null != jsonObject) {
            if ("0".equals(jsonObject.get("result"))) {
                responseResult.setStatus(false);
                responseResult.setMsg(jsonObject.get("errorMsg").toString());
            } else {
                responseResult.setStatus(true);
                resultMap.put("marketName", jsonObject.get("marketName"));//市场名
                resultMap.put("wholesalerName", jsonObject.get("wholesalerName"));//经销商名
                resultMap.put("boothNo", jsonObject.get("boothNo"));//摊位号
                resultMap.put("phoneNum", jsonObject.get("phoneNum"));//电话
                resultMap.put("typeNa", jsonObject.get("typeNa"));//品类
                resultMap.put("datas", jsonObject.get("datas"));//数据集合
                responseResult.setMsg("查询成功");
                responseResult.setResultMap(resultMap);
            }
            return responseResult;
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("未查询到该经销商");
            return responseResult;
        }

    }

    @RequestMapping(value = "scanCodeInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult scanCodeInfo(String wholesalerId, int index) {
        ResponseResult responseResult = new ResponseResult();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("wholesalerId", wholesalerId);
        //  接口地址：http://192.168.1.20:8080/fd-web/purchaseRecords/fd_wholesale_entrRecord
        String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_wholesale_entrRecord";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        String result = HttpUtils.postWithJson(entrRecordUrl, jsonStr);
        JSONObject jsonObject = JSON.parseObject(result);

        if (null != jsonObject) {
            if ("0".equals(jsonObject.get("result"))) {
                responseResult.setStatus(false);
                responseResult.setMsg(jsonObject.get("errorMsg").toString());
            } else {
                responseResult.setStatus(true);
                resultMap.put("data", ((List<Object>) jsonObject.get("datas")).get(index));//数据
                responseResult.setMsg("查询成功");
                responseResult.setResultMap(resultMap);
            }
            return responseResult;
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("未查询到该经销商");
            return responseResult;
        }

    }

    @RequestMapping(value = "sign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sign(String url) {
        String accessToken = WxUtils.getAccessToken();

        String jsapiTicket = WxUtils.getJsApiTicket(accessToken);
        return Sign.sign(jsapiTicket, url);
    }
}
