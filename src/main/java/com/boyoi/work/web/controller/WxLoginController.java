package com.boyoi.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.work.utils.WxSessionRefreshKeyUtil;
import com.boyoi.work.utils.WxUserInfoSaveUtil;
import com.boyoi.work.utils.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录
 * @author Chenj
 */
@Controller
public class WxLoginController {

    @Autowired
    private WxSessionRefreshKeyUtil wxSessionRefreshKeyUtil;
    @Autowired
    private WxUserInfoSaveUtil wxUserInfoSaveUtil;

    @RequestMapping(value = "wxLogin",method = RequestMethod.GET)
    public String wxLogin(){
        return "login";
    }

    @RequestMapping(value = "wxLoginFun")
    public String wxLoginFun(String tel, String smsCode, HttpSession session, HttpServletResponse response, Model model){
        // 验证帐号或code是否正确
//        if (null != smsCode){
//            if (!smsCode.equals(session.getAttribute("smsCode"))){
//                model.addAttribute("error","验证码不正确！");
//                return "login";
//            }
//        }else {
//            model.addAttribute("error","验证码不能为空！");
//            return "login";
//        }

        // 获得token
        // http://192.168.1.76/oauth/token?client_id=ANDROID&grant_type=password&loginName=www&password=123
        String oauthUrl = WxUtils.baseUrl+ "/oauth/token?client_id=ANDROID&grant_type=password&loginName="+tel+"&password=123456780";

        String result = HttpUtils.get(oauthUrl);
        if(null != result){
            JSONObject jsonObject = JSON.parseObject(result);
            Map<String,Object> statusMap = JSON.parseObject(jsonObject.get("status").toString(), HashMap.class);
            String code = statusMap.get("code")+"";
            if("1".equals(code)){
                Map<String,Object> map = JSON.parseObject(JSON.toJSONString(jsonObject.get("data")), HashMap.class);
                if (null != map.get("access_token")){
                    // 获得用户详细信息
                    String userInfoUrl = WxUtils.baseUrl + WxUtils.USER_INFO_URL+"?token="+map.get("access_token");
                    Map<String,String> paraMap = new HashMap<>();
                    paraMap.put("loginName",tel);
                    String jsonStr = "jsonStr="+JSON.toJSONString(paraMap);
                    JSONObject userInfo = JSON.parseObject(HttpUtils.postWithJson(userInfoUrl, jsonStr));
                    // 批发市场经营户才能登录
                    if (null != userInfo){
                        Map<String,Map<String,Object>> dataMap = JSON.parseObject(JSON.toJSONString(userInfo.get("data")), HashMap.class);
                        if ("W".equals(dataMap.get("userInfo").get("userType")) || "O".equals(dataMap.get("userInfo").get("userType"))){
                            // 保存用户cookie
                            Cookie cookie = new Cookie("t", (String)map.get("access_token"));

                            // 3年不过期
                            cookie.setMaxAge(60*60*24*1095);
                            cookie.setPath("/");
                            cookie.setHttpOnly(true);
                            response.addCookie(cookie);
                            // 判断以前是否存在，存在就删除
                            Map<String, JSONObject> all = WxSessionRefreshKeyUtil.getAll();

                            // 删除已经存在的
                            for (Map.Entry<String, JSONObject> entry : all.entrySet()){
                                JSONObject existUserInfo = WxUserInfoSaveUtil.get(entry.getKey());
                                if (null != existUserInfo){
                                    wxSessionRefreshKeyUtil.del(entry.getKey());
                                    wxUserInfoSaveUtil.del(entry.getKey());
                                }
                            }

                            // 添加到工具类中
                            wxSessionRefreshKeyUtil.add(cookie.getValue(), jsonObject);
                            wxUserInfoSaveUtil.add(cookie.getValue(), JSONObject.parseObject(JSON.toJSONString(dataMap)));
                        }else {
                            // 登录失败
                            model.addAttribute("error","只有“批发市场经营户”才能使用本平台！");
                            return "login";
                        }
                    }else {
                        // 登录失败
                        model.addAttribute("error","获得用户基础信息失败！请联系管理员！");
                        return "login";
                    }
                    return "redirect:wxAdmin";
                }else if (null != jsonObject.get("status")){
                    // 登录失败
                    model.addAttribute("error",jsonObject.get("msg"));
                    return "login";
                }else {
                    // 登录失败
                    model.addAttribute("error","登录失败！请联系管理员！");
                    return "login";
                }
            }else{
                // 登录失败
                model.addAttribute("error",statusMap.get("msg"));
                return "login";
            }

        }else {
            // 登录失败
            model.addAttribute("error","服务器异常！请联系管理员！");
            return "login";
        }

    }

    // 检查是否填写完成
    @RequestMapping(value = "checkFill")
    @ResponseBody
    public JSONObject checkFill(HttpServletRequest request){
        String url = WxUtils.baseUrl  + WxUtils.SWTASK_ISPASS_URL;
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);

        Map<String, String> para  = new HashMap<>();
        para.put("applicant", (String)((JSONObject)userInfo.get("userInfo")).get("userId"));
        para.put("userType", (String) ((JSONObject) userInfo.get("userInfo")).get("userType"));

        Map<String, String> requestPara  = new HashMap<>();
        requestPara.put("jsonStr", JSON.toJSONString(para));

        return HttpUtils.getWithToken(url, requestPara);
    }

    // 填写信息
    @RequestMapping(value = "wxFillInfo")
    public String wxFillInfo(HttpServletRequest request, Model model){
        JSONObject obj = WxUserInfoSaveUtil.get(request);
        JSONObject userInfo = (JSONObject) obj.get("userInfo");
        Map<String, String> map = new HashMap<>();
        map.put("keyWords", "");
        JSONObject jsonObject = HttpUtils.postWithToken(WxUtils.baseUrl + WxUtils.WHOLESALE_MARKET, "jsonStr=" + JSON.toJSONString(map));
        model.addAttribute("jsonObject", jsonObject);
        if ("P".equals(userInfo.get("nature"))){
            // 完善个人信息
            return "weixinLogin/personalData";
        }else if ("E".equals(userInfo.get("nature"))){
            // 完善企业信息
            return "weixinLogin/enterpriseData";
        }
        // 找不到返回登录
        return "redirect:/wxLogin";
    }

    // 进货统计
    @RequestMapping(value = "wxAdmin")
    public String wxAdminPurchaseRecord(HttpServletRequest request){
        JSONObject jsonObject = WxUserInfoSaveUtil.get(request);
        if (null != jsonObject){
            return "weixinLogin/admin";
        }else {
            return "redirect:/wxLogin";
        }

    }

    // 销售统计
    @RequestMapping(value = "wxAdminSaleRecord")
    public String wxAdminSaleRecord(HttpServletRequest request, Model model){
        String saleRecordUrl = WxUtils.baseUrl  + WxUtils.SALE_RECORD_URL;
        Map<String, String> para  = new HashMap<>();
        JSONObject jsonObject = WxUserInfoSaveUtil.get(request);
        para.put("wholesaleId", (String)((JSONObject)jsonObject.get("userInfo")).get("wholesalerId"));
        Date endTime = new Date();
        Date startTime = DateTimeUtil.timeSet(endTime,
                new int[]{Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND},
                new int[]{1, 0, 0, 0});
        para.put("startTime", DateTimeUtil.yyyyMMddhhmmss(startTime));
        para.put("endTime", DateTimeUtil.yyyyMMddhhmmss(endTime));

        model.addAttribute("month", DateTimeUtil.yyyyMM(endTime));
        model.addAttribute("result", HttpUtils.postWithToken(saleRecordUrl, "jsonStr="+JSON.toJSONString(para)));
        return "weixinLogin/adminSaleRecord";
    }
}
