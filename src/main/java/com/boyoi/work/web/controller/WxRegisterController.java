package com.boyoi.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.Bean2MapUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.work.domain.BoothType;
import com.boyoi.work.domain.UserInfo;
import com.boyoi.work.domain.UserInfoPhoto;
import com.boyoi.work.domain.WholesalerUserData;
import com.boyoi.work.utils.GenSmsCode;
import com.boyoi.work.utils.SmsHelper;
import com.boyoi.work.utils.WxUserInfoSaveUtil;
import com.boyoi.work.utils.WxUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * 微信登录
 *
 * @author Chenj
 */
@Controller
public class WxRegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(WxRegisterController.class);

    private static final String SmsTemplateCode = "SMS_16505287";
    private static final String SmsFreeSignName = "食安互联";
    private static final String SmsAppKey = "23467978";
    private static final String SmsAppSecret = "0437844b31901863d29f9cf916b4f99a";

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(UserInfo user, Model model, String areaName) {
        model.addAttribute("user", user);
        model.addAttribute("areaName", areaName);
        return "register/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerFun(UserInfo user,String... booth) {
        ResponseResult responseResult =new ResponseResult();
        List<BoothType> boothTypes = new ArrayList<>();
        for(String bo :booth){
            BoothType boothType = new BoothType();
            boothType.setBoothCode(bo);
            boothTypes.add(boothType);
        }
        user.setBoothInfo(boothTypes);
        user.setRegType("C");
        // 注册
        Map<String, Object> para = Bean2MapUtil.bean2Map(user);
        String jsonStr = "jsonStr=" + JSON.toJSONString(para);
        JSONObject jsonObject = HttpUtils.postWithToken(WxUtils.baseUrl + WxUtils.REGISTER_URL,jsonStr );
        if (null != jsonObject) {
            Map<String,Object> status = JSON.parseObject(JSON.toJSONString(jsonObject.get("status")), HashMap.class);
            String code = status.get("code").toString();
            if ("1".equals(code)) {
                // 注册成功，跳转到首页
                responseResult.setStatus(true);
            } else {
                responseResult.setStatus(false);
                responseResult.setMsg(status.get("msg").toString());
            }
        }else{
            responseResult.setStatus(false);
            responseResult.setMsg("系统错误，请联系管理员！");
        }
        return responseResult;
    }


    // 完善信息
    @RequestMapping(value = "fillInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerEnterprise(WholesalerUserData wholesalerUserData,
                                             String enterpriseFromWx, String idPhoto1FromWx, String idPhoto2FromWx,
                                             HttpServletRequest request, Model model,
                                             String... booth
    ) {

        ResponseResult responseResult = new ResponseResult();

        String accessToken = WxUtils.getAccessToken2();

        File[] files = new File[3];
        if (null != enterpriseFromWx && !"".equals(enterpriseFromWx)) {
            File file = WxUtils.downloadMedia(accessToken, enterpriseFromWx);
            if (null != file) {
                files[0] = file;
                wholesalerUserData.getPicList().add(new UserInfoPhoto(file.getName(), "S"));
            } else {
                LOG.warn("下载微信图片失败！");
            }
        }
        if (null != idPhoto1FromWx && !"".equals(idPhoto1FromWx)) {
            File file = WxUtils.downloadMedia(accessToken, idPhoto1FromWx);
            if (null != file) {
                files[1] = file;
                wholesalerUserData.getPicList().add(new UserInfoPhoto(file.getName(), "F"));
            } else {
                LOG.warn("下载微信图片失败！");
            }
        }
        if (null != idPhoto2FromWx && !"".equals(idPhoto2FromWx)) {
            File file = WxUtils.downloadMedia(accessToken, idPhoto2FromWx);
            if (null != file) {
                files[2] = file;
                wholesalerUserData.getPicList().add(new UserInfoPhoto(file.getName(), "B"));
            } else {
                LOG.warn("下载微信图片失败！");
            }
        }

        JSONObject jsonObject = WxUserInfoSaveUtil.get(request);
        if (null != jsonObject) {
            // 经营食用农产品主要品种
            if (null != booth) {
                List<BoothType> boothTypeList = new ArrayList<>();
                for (String b : booth) {
                    BoothType boothType = new BoothType();
                    boothType.setBoothCode(b);
                    boothTypeList.add(boothType);
                }
                wholesalerUserData.setBoothList(boothTypeList);
            }

            // 获得基础信息
            JSONObject userInfo = (JSONObject) (jsonObject.get("userInfo"));
            wholesalerUserData.setWholesalerId((String) userInfo.get("wholesalerId"));
            wholesalerUserData.setNature((String) userInfo.get("nature"));

            Map<String, String> map = new HashMap<>();
            map.put("jsonStr", JSON.toJSONString(wholesalerUserData));
            // 请求
            JSONObject result = HttpUtils.postWithToken(WxUtils.baseUrl + WxUtils.WHOLESALER_FILLINFO, map, files);

            // 处理结果
            if (null != result) {
                responseResult.setStatus("1".equals(result.get("result")));
                responseResult.setMsg((String) result.get("errorMsg"));
            } else {
                responseResult.setStatus(false);
                responseResult.setMsg("提交失败！");
            }

        } else {
            responseResult.setStatus(false);
            responseResult.setMsg("获得基础信息失败！请重新登录！");
        }
        return responseResult;
    }

    // 发送手机验证码
    @RequestMapping(value = "sendSMSCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult sendSMSCode(String tel, HttpSession session) {
        ResponseResult responseResult = new ResponseResult();
        Date date = SmsHelper.lastSendSms.get(session.getId());
        if (null != date) {
            // 小于60秒。提示不要重复发送短信
            if (new Date().getTime() - date.getTime() < 60 * 1000) {
                responseResult.setStatus(false);
                responseResult.setMsg("请间隔60秒以后再次发送短信！");
                return responseResult;
            }
        }

        if (null != tel && tel.length() == 11) {
            long l = Long.parseLong(tel);
            if (18999999999l >= l && l >= 13000000000l) {
                try {
                    TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", SmsAppKey, SmsAppSecret);
                    AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
                    req.setExtend("");
                    req.setSmsType("normal");
                    req.setSmsFreeSignName(SmsFreeSignName);
                    String smsCode = GenSmsCode.random4Number();
                    req.setSmsParamString("{number:'" + smsCode + "'}");
                    req.setRecNum(tel);
                    req.setSmsTemplateCode(SmsTemplateCode);
                    AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
                    if (null != rsp.getResult() && rsp.getResult().getSuccess()) {
                        responseResult.setStatus(true);
                        responseResult.setMsg("发送成功！请注意查收！");
                        session.setAttribute("smsCode", smsCode);
                        SmsHelper.lastSendSms.put(session.getId(), new Date());
                    } else {
                        LOG.info("发送短信失败！Tel{},msg:{}", tel, rsp.getMsg());
                        responseResult.setStatus(false);
                        if ("触发业务流控".equals(rsp.getSubMsg())) {
                            responseResult.setMsg("发送次数过多！请稍后在试！");
                        } else {
                            responseResult.setMsg("发送失败！请联系管理员！");
                        }
                    }
                    return responseResult;
                } catch (Exception e) {
                    responseResult.setStatus(false);
                    responseResult.setMsg(e.getMessage());
                    return responseResult;
                }
            }
        }

        responseResult.setStatus(false);
        responseResult.setMsg("手机号格式不正确!");

        return responseResult;
    }

    // 完善注册信息UI
    @RequestMapping(value = "registerData", method = RequestMethod.POST)
    public String registerData(UserInfo user, Model model, String areaName, HttpSession session) {
        String[] ids = user.getAreaId().split(",");
        user.setAreaId(ids[ids.length - 1]);
        model.addAttribute("user", user);
        model.addAttribute("areaName", areaName);
        session.removeAttribute("smsCode");
        //查询所有市场
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> resultMap;
        Map<String, Object> deptMap;
        dataMap.put("areaId", user.getAreaId());
        dataMap.put("deptName", "");
        dataMap.put("deptType", "W");
        String entrRecordUrl = WxUtils.baseUrl + "/department/fd_get_dept";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            deptMap = JSON.parseObject(JSON.toJSONString(resultMap.get("data")), HashMap.class);
            model.addAttribute("deptList", deptMap.get("deptList"));

        }
        //查询所有摊位类型
        Map<String, Object> resultMap2;
        Map<String, Object> deptMap2;
        String entrRecordUrl2 = WxUtils.baseUrl + "/userInfo/fd_user_boothTypeInit";
        JSONObject jsonObject2 = HttpUtils.getWithToken(entrRecordUrl2);
        if (null != jsonObject2) {
            resultMap2 = JSON.parseObject(JSON.toJSONString(jsonObject2), HashMap.class);
            deptMap2 = JSON.parseObject(JSON.toJSONString(resultMap2.get("data")), HashMap.class);
            model.addAttribute("boothList", deptMap2.get("booth"));
        }
        return "register/registerData";


    }

    // 验证验证码是否正确
    @RequestMapping(value = "checkCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerData(String smsCode, HttpSession session) {
        ResponseResult responseResult = new ResponseResult();
        // 判断验证码是否正确
        String code = (String) session.getAttribute("smsCode");
        if ("".equals(code) || code == null) {
            responseResult.setStatus(false);
            responseResult.setMsg("请发送验证码！");
        } else {
            if (!smsCode.equals(code)) {
                responseResult.setStatus(false);
                responseResult.setMsg("验证码不正确！");
            } else {
                responseResult.setStatus(true);
            }
        }
        return responseResult;
    }


}
