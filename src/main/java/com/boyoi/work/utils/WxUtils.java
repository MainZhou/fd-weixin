package com.boyoi.work.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.core.utils.PropertiesUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信工具类
 *
 * @author Chenj
 */
public class WxUtils {
    public static String baseUrl = "http://localhost";

    // 用户基本信息
    public static final String USER_INFO_URL = "/user/fd_user_info";
    // 注册
    public static final String REGISTER_URL = "/user/fd_all_user_reg";
    // 进货历史记录
    public static final String PURCHASE_RECORDS_URL = "/purchaseRecords/fd_ws_entrRecord";
    // 销售历史记录
    public static final String SALE_RECORD_URL = "/saleRecords/fd_ws_dealRecord";
    //供应商、批发商信息审核是否通过
    public static final String SWTASK_ISPASS_URL = "/fillinfo/fd_swTask_isPass";
    //获取市场信息接口
    public static final String WHOLESALE_MARKET =  "/wholesaleMarket/fd_market_list";
    //完善销售者基础信息
    public static final String WHOLESALER_FILLINFO = "/fillinfo/fd_wholesaler_fillInfo";

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    public String path = this.getClass().getResource("/").getPath();
    //文件路径
    public String filePath = this.getClass().getResource("/../../").getPath();

//    //测试平台微信appID
//    private static final String APPID = "wxb4a8afa35245b371";
//    //测试平台微信appsecret
//    private static final String APPSECRET = "2ffea159d7af122d6221a466f9b4f551";

    //正式平台微信appID
    private static final String APPID = "wxae01505937f570e6";
    //正式平台微信appsecret
    private static final String APPSECRET = "8e633c5c57838969d43145af1c68a48e";

    public String getPath() {
        return path;
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * 获取页面access_token
     *
     * @return access_token
     */
    //获取AccessToken的接口url
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    public static String getAccessToken() {
        WxUtils wx = new WxUtils();
        //获取文件
        PropertiesUtil propertiesUtil = new PropertiesUtil("weixinCode.properties");
        //获取存入时间
        String accessTokenTime = propertiesUtil.readString("accessToken.Time");
        //获取accessToken
        String accessToken = propertiesUtil.readString("accessToken.Code");
        //是否第一次调用
        if (accessToken == null || "".equals(accessToken)) {//第一次调用
            //调用接口获取accessToken 并存入文件中
            String result = HttpUtils.get(ACCESS_TOKEN_URL + "?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET);
            JSONObject jsonObject = JSON.parseObject(result);
            accessToken = jsonObject.get("access_token").toString();
            Map<String, String> map = new HashMap<>();
            map.put("accessToken.Code=", accessToken);
            map.put("accessToken.Time=", DateTimeUtil.yyyyMMddhhmmss(new Date()));
            //保存进入文件
            FileUtils.saveFile(wx.getPath()+ "weixinCode.properties", map);
        } else {//已调用过
            Date codeTime = DateTimeUtil.yyyyMMddhhmmss(accessTokenTime);
            Date now = new Date();
            Long times = DateTimeUtil.getSecond(codeTime, now);
            //判断是否超时
            if (times > 7199) {//已超时，需重新获取，并存入文件
                String result = HttpUtils.get(ACCESS_TOKEN_URL + "?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET);
                JSONObject jsonObject = JSON.parseObject(result);
                accessToken = jsonObject.get("access_token").toString();
                Map<String, String> map = new HashMap<>();
                map.put("accessToken.Code=", accessToken);
                map.put("accessToken.Time=", DateTimeUtil.yyyyMMddhhmmss(new Date()));
                FileUtils.saveFile(wx.getPath()+ "weixinCode.properties", map);
            }
        }
        return accessToken;
    }

    /**
     * 获取js_api调用凭证
     */
    //获取JsApiTicket的接口url
    private static final String JS_API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public static String getJsApiTicket(String accessToken) {
        WxUtils wx = new WxUtils();
        //获取文件
        PropertiesUtil propertiesUtil = new PropertiesUtil("weixinCode.properties");
        String jsapiTicket = propertiesUtil.readString("jsapiTicket.Code");
        String jsapiTicketTime = propertiesUtil.readString("jsapiTicket.Time");
        if (jsapiTicket == null || "".equals(jsapiTicket)) {
            String result = HttpUtils.get(JS_API_TICKET + "?access_token=" + accessToken + "&type=jsapi");
            JSONObject jsonObject = JSON.parseObject(result);
            jsapiTicket = jsonObject.get("ticket").toString();
            Map<String, String> map = new HashMap<>();
            map.put("jsapiTicket.Code=", jsapiTicket);
            map.put("jsapiTicket.Time=", DateTimeUtil.yyyyMMddhhmmss(new Date()));
            FileUtils.saveFile(wx.getPath()+ "weixinCode.properties", map);
        } else {
            Date codeTime = DateTimeUtil.yyyyMMddhhmmss(jsapiTicketTime);
            Date now = new Date();
            Long times = DateTimeUtil.getSecond(codeTime, now);
            if (times > 7199) {
                String result = HttpUtils.get(JS_API_TICKET + "?access_token=" + accessToken + "&type=jsapi");
                JSONObject jsonObject = JSON.parseObject(result);
                jsapiTicket = jsonObject.get("ticket").toString();
                Map<String, String> map = new HashMap<>();
                map.put("jsapiTicket.Code=", jsapiTicket);
                map.put("jsapiTicket.Time=", DateTimeUtil.yyyyMMddhhmmss(new Date()));
                FileUtils.saveFile(wx.getPath() + "weixinCode.properties", map);
            }
        }


        return jsapiTicket;
    }

    /**
     * 获取公众号的全局唯一票据access_token
     */
    //获取access_token的接口url
    private static final String ACCESS_TOKEN2 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";

    public static String getAccessToken2() {
        WxUtils wx = new WxUtils();
        //获取文件
        PropertiesUtil propertiesUtil = new PropertiesUtil("weixinCode.properties");
        //获取存入时间
        String accessTokenTime = propertiesUtil.readString("accessToken2.Time");
        //获取accessToken
        String accessToken = propertiesUtil.readString("accessToken2.Code");
        //是否第一次调用
        if (accessToken == null || "".equals(accessToken)) {//第一次调用
            //调用接口获取accessToken 并存入文件中
            String result = HttpUtils.get(ACCESS_TOKEN2 + "appid=" + APPID + "&secret=" + APPSECRET);
            JSONObject jsonObject = JSON.parseObject(result);
            accessToken = jsonObject.get("access_token").toString();
            Map<String, String> map = new HashMap<>();
            map.put("accessToken2.Code=", accessToken);
            map.put("accessToken2.Time=", DateTimeUtil.yyyyMMddhhmmss(new Date()));
            //保存进入文件
            FileUtils.saveFile(wx.getPath() + "weixinCode.properties", map);
        } else {//已调用过
            Date codeTime = DateTimeUtil.yyyyMMddhhmmss(accessTokenTime);
            Date now = new Date();
            Long times = DateTimeUtil.getSecond(codeTime, now);
            //判断是否超时
            if (times > 7199) {//已超时，需重新获取，并存入文件
                String result = HttpUtils.get(ACCESS_TOKEN2 + "appid=" + APPID + "&secret=" + APPSECRET);
                JSONObject jsonObject = JSON.parseObject(result);
                accessToken = jsonObject.get("access_token").toString();
                Map<String, String> map = new HashMap<>();
                map.put("accessToken2.Code=",  accessToken);
                map.put("accessToken2.Time=",  DateTimeUtil.yyyyMMddhhmmss(new Date()));
                FileUtils.saveFile(wx.getPath()+ "weixinCode.properties", map);
            }
        }
        return accessToken;
    }



    /**
     * 下载多媒体
     * @param accessToken 微信全局唯一凭证
     * @param mediaId 媒体ID
     * @return 媒体流
     */
    //下载多媒体接口url
    private static final String DOWNLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    public static File downloadMedia(String accessToken, String mediaId) {
        String requestUrl = DOWNLOAD_MEDIA.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        BufferedInputStream bis;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            bis = new BufferedInputStream(conn.getInputStream());

            WxUtils wxUtils = new WxUtils();
            String path = wxUtils.getFilePath() + File.separator + "attached" + File.separator + mediaId+".jpg";
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buf = new byte[8096];
            int len;
            while ((len=bis.read(buf)) > 0){
                fos.write(buf,0,len);
            }
            fos.close();
            bis.close();
            conn.disconnect();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
