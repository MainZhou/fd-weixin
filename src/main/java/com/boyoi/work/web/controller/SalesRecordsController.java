package com.boyoi.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.work.utils.Sign;
import com.boyoi.work.utils.WxUserInfoSaveUtil;
import com.boyoi.work.utils.WxUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 消费者查询
 *
 * @author zhouJL
 */
@Controller
@RequestMapping(value = "salesRecords")
public class SalesRecordsController {


    protected Logger LOG;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listUI(HttpSession session) {
        return "salesRecords/salesRecords";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult list(HttpServletRequest request, int index,int typeIndex) {
        ResponseResult responseResult = new ResponseResult();
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        Map<String, Object> resultMap;
        Map<String, Object> resultMap2;
        Map<String, String> dataMap = new HashMap<>();
        Date now = new Date();
        if(userInfo!=null){
            dataMap.put("wholesalerId", (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId"));
            if (index == 0) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(now, Calendar.DATE, -30)));
            } else if (index == 1) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -60)));
            } else {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -90)));
            }
            dataMap.put("endTime", DateTimeUtil.yyyyMMddhhmmss(now));
        }
        dataMap.put("wholesalerCode","");
        dataMap.put("pageNum","1");
        dataMap.put("pageSize","100");
        //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
        String entrRecordUrl;
        if(typeIndex==0){
            entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_entrRecordNew";//进货查询
        }else if(typeIndex==1){
            entrRecordUrl = WxUtils.baseUrl + "/saleRecords/fd_ws_dealRecordNew";//销售查询
        }else{
            dataMap.put("wmId", (String) ((JSONObject) userInfo.get("userInfo")).get("deptId"));
            dataMap.put("data", "");
            entrRecordUrl = WxUtils.baseUrl + "/marketTypes/fd_mt_priceInit";//电子秤
        }
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            String code = (String) JSON.parseObject(resultMap.get("status").toString(), HashMap.class).get("code");
            if (code != null && "1".equals(code)) {
                if(!"[]".equals(resultMap.get("data").toString())){
                    responseResult.setMsg("查询成功");
                    responseResult.setStatus(true);
                    resultMap=JSON.parseObject(resultMap.get("data").toString(), HashMap.class);
                    String path = null;
                    if(typeIndex==2){
                        entrRecordUrl=WxUtils.baseUrl + "/marketDetectimg/fd_md_record";//市场抽查凭证
                        dataMap=new HashMap<>();
                        dataMap.put("wmId", (String) ((JSONObject) userInfo.get("userInfo")).get("deptId"));
                        dataMap.put("orderBy","O");
                        dataMap.put("pageNum","1");
                        dataMap.put("pageSize","1");
                        String jsonStr2 = "jsonStr=" + JSON.toJSONString(dataMap);
                        JSONObject jsonObject2 = HttpUtils.postWithToken(entrRecordUrl, jsonStr2);
                        if (null != jsonObject2) {
                            resultMap2=JSON.parseObject(JSON.toJSONString(jsonObject2), HashMap.class);
                            String code2 = (String) JSON.parseObject(resultMap2.get("status").toString(), HashMap.class).get("code");
                            if (code2 != null && "1".equals(code2)) {
                                if(!"[]".equals(resultMap2.get("data").toString())){
                                    resultMap2=JSON.parseObject(resultMap.get("data").toString(), HashMap.class);
                                    path=resultMap2.get("path").toString();
                                }
                            }
                        }
                    }
                    resultMap.put("path",path);
                    responseResult.setResultMap(resultMap);
                }else{
                    // 查询失败
                    responseResult.setStatus(false);
                    responseResult.setMsg("没有数据");
                }
            } else {
                // 查询失败
                responseResult.setStatus(false);
                responseResult.setMsg("没有数据");
            }
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }
}
