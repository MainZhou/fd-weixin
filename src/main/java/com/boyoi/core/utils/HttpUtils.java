package com.boyoi.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.enums.CommonExceptionEnum;
import com.boyoi.core.exception.CommonException;
import com.boyoi.work.utils.Stream2String;
import com.boyoi.work.utils.WxSessionRefreshKeyUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Http原生操作 帮助类
 * 返回string
 * @author Chenj
 */
public class HttpUtils {

    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static String get(String url){
        try {
            URL u = new URL(url);
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //接收数据.并组装成StringBuilder
            return getResultString(urlConnection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static JSONObject getWithToken(String url){
        try {
            Map<String, String> para = new HashMap<>();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("t".equals(cookie.getName())) {
                        para.put("token", WxSessionRefreshKeyUtil.get(cookie.getValue()));
                    }
                }
            }
            URL u = new URL(makeurl(url, para));
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //接收数据.并组装成StringBuilder
            String result = getResultString(urlConnection);
            return execResult(result, urlConnection);
        }catch (CommonException e1){
            throw e1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static JSONObject getWithToken(String url, Map<String, String> requestPara){
        try {
            Map<String, String> para = new HashMap<>();
            if (null != requestPara){
                para.putAll(requestPara);
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("t".equals(cookie.getName())) {
                        para.put("token", WxSessionRefreshKeyUtil.get(cookie.getValue()));
                    }
                }
            }
            URL u = new URL(makeurl(url, para));
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //接收数据.并组装成StringBuilder
            String result = getResultString(urlConnection);
            return execResult(result, urlConnection);
        }catch (CommonException e1){
            throw e1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static JSONObject getWithToken(String url, Map<String, String> requestPara,String token){
        try {
            Map<String, String> para = new HashMap<>();
            if (null != requestPara){
                para.putAll(requestPara);
            }

            URL u = new URL(makeurl(url, para));
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setRequestProperty("token", token);
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //接收数据.并组装成StringBuilder
            String result = getResultString(urlConnection);
            return execResult(result, urlConnection);
        }catch (CommonException e1){
            throw e1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得结果(string)
     * @param urlConnection urlConnection
     */
    private static String getResultString(HttpURLConnection urlConnection) throws IOException {
        InputStream is = urlConnection.getInputStream();
        String result = Stream2String.inputStream2String(is);
        is.close();
        urlConnection.disconnect();
        return result;
    }

    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static JSONObject postWithToken(String url, String json){
        try {
            //配置连接属性
            Map<String, String> para = new HashMap<>();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("t".equals(cookie.getName())) {
                         para.put("token", WxSessionRefreshKeyUtil.get(cookie.getValue()));
                    }
                }
            }
            URL u = new URL(makeurl(url, para));
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //写入json数据到流中.字节必须转为utf-8
            BufferedOutputStream bos = new BufferedOutputStream(urlConnection.getOutputStream()) ;
            bos.write(json.getBytes("UTF-8"));
            bos.flush();
            bos.close();

            //接收数据.并组装成StringBuilder
            String result = getResultString(urlConnection);
            return execResult(result, urlConnection);
        }catch (CommonException e1){
            throw e1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get请求一个url,并组装参数
     * @param url url
     * @param para 参数
     * @return 返回的字符
     */
    public static String get(String url, Map<String, String> para){
        try {
            URL u = new URL(makeurl(url, para));
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //接收数据.并组装成StringBuilder
            return getResultString(urlConnection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用post操作一个数据(会以JSON格式发送)
     * @param url 请求的URL地址
     * @param json 发送的数据
     * @return StringBuilder对象
     */
    public static String postWithJson(String url,String json){
        BufferedOutputStream bos = null;
        try {
            URL u = new URL(url);
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            //写入json数据到流中.字节必须转为utf-8
            bos = new BufferedOutputStream(urlConnection.getOutputStream()) ;
            bos.write(json.getBytes("UTF-8"));
            bos.flush();
            bos.close();

            //接收数据.并组装成StringBuilder
            return getResultString(urlConnection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != bos)
                    bos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 上传附件
     * @param url  URL地址
     * @return 结果
     */
    public static JSONObject postWithToken(String url, Map<String, String> textMap ,File[] files){
        try{

            //构建一个URL地址

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie[] cookies = request.getCookies();
            String token=null;
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("t".equals(cookie.getName())) {
                        token = WxSessionRefreshKeyUtil.get(cookie.getValue());
                        break;
                    }
                }
            }

            //构建一个URL对象
            URL u = new URL(url);

            //打开URL的链结，并转成HttpURLConnection
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
            urlConnection.setRequestProperty("token", token); // 加入到header中
            urlConnection.setConnectTimeout(30000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("contentType", "utf-8");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            String BOUNDARY = "----WebKitFormBoundarynF4Tb55G6fce13Ek";
            urlConnection.setRequestProperty("Content-Type","multipart/form-data; boundary="+BOUNDARY);
            //写入json数据到流中.字节必须转为utf-8
            BufferedOutputStream bos = new BufferedOutputStream(urlConnection.getOutputStream()) ;

            // 写入文本信息
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName =  entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                bos.write(strBuf.toString().getBytes("utf-8"));
                bos.write("\r\n".getBytes());
            }

            for (int i=0;i<files.length;i++){
                //打开输出，把传来的输入流数据写进去
                if(null != files[i] && files[i].canRead()){

                    /**
                     * 写入文本
                     */
                    StringBuffer sb = new StringBuffer();
                    sb.append("--").append(BOUNDARY).append("\r\n");
                    sb.append("Content-Disposition: form-data; name=\"file"+i+"\"; filename=\""+files[i].getName()+"\"\r\n\r\n");

                    bos.write(sb.toString().getBytes());

                    /**
                     * 写入文件的数据
                     */
                    BufferedInputStream bis = new BufferedInputStream(new DataInputStream(new FileInputStream(files[i])));
                    byte[] b =new byte[1024];
                    int len;
                    while ((len=bis.read(b))>0){
                        bos.write(b,0,len);
                    }

                    bos.write("\r\n".getBytes());

                    bis.close();
                }
            }

            /**
             * 写入结尾
             */
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            bos.write(end_data);
            bos.flush();
            bos.close();

            //获得返回的数据
            String result = getResultString(urlConnection);

            return execResult(result, urlConnection);
        }catch (CommonException e1){
            throw e1;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理结果
     */
    private static JSONObject execResult(String result, HttpURLConnection urlConnection) throws IOException {
        if ("timeout".equals(urlConnection.getHeaderField("sessionStatus"))){
            // 抛出token过期异常
            throw new CommonException(CommonExceptionEnum.E1);
        }else if (403 == urlConnection.getResponseCode()){
            throw new CommonException(CommonExceptionEnum.E3);
        }else {
            return JSONObject.parseObject(result);
        }
    }

    /**
     * 获得项目根地址
     * 如:http://192.168.1.76/xeg/
     * @param servletRequest HttpServletRequest
     * @return 项目根地址
     */
    public static String getBasePath(ServletRequest servletRequest){
        return servletRequest.getScheme() + "://" +
                servletRequest.getLocalAddr() + ":" +
                servletRequest.getLocalPort() +
                servletRequest.getServletContext().getContextPath();
    }

    /**
     * 解析请求参数param,并构建一个请求地址
     */
    private static String makeurl(String url, Map<String, String> param) {
        //参数不为NULL，并且含有内容
        if(null != param && !param.isEmpty()){
            Set<Map.Entry<String, String>> set = param.entrySet();
            //设置第一个参数标记
            boolean flag=true;
            // url已经包含参数了，设为false
            if (url.contains("?")){
                flag = false;
            }
            //迭代
            for(Map.Entry<String,String> entry:set){
                if(flag){
                    url = url + "?" + entry.getKey() + "=" + entry.getValue();
                    flag=false;//设置为false，下次就不会执行
                }else {
                    url = url + "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
        }
        return url;
    }
}
