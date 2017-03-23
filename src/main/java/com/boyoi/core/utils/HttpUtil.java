package com.boyoi.core.utils;

import javax.servlet.ServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Http原生操作 帮助类
 *
 * @author Chenj
 */
public class HttpUtil {

    /**
     * get请求一个url
     * @param url url
     * @return url返回的数据
     */
    public static StringBuilder get(String url){
        BufferedInputStream bis = null;
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
            bis = new BufferedInputStream(urlConnection.getInputStream());
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len=bis.read(b))>0){
                sb.append(new String(b,0,len,"UTF-8"));
            }
            return sb;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != bis)
                    bis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get请求一个url,并组装参数
     * @param url url
     * @param para 参数
     * @return 返回的字符
     */
    public static StringBuilder get(String url, Map<String, String> para){
        BufferedInputStream bis = null;
        StringBuilder sb = new StringBuilder();
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
            bis = new BufferedInputStream(urlConnection.getInputStream());
            byte[] b = new byte[1024];
            int len;
            while ((len=bis.read(b))>0){
                sb.append(new String(b,0,len,"UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != bis)
                    bis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return sb;
    }

    /**
     * 使用post操作一个数据(会以JSON格式发送)
     * @param url 请求的URL地址
     * @param json 发送的数据
     * @return StringBuilder对象
     */
    public static StringBuilder postWithJson(String url,String json){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL u = new URL(url);
            //配置连接属性
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
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
            bis = new BufferedInputStream(urlConnection.getInputStream());
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len=bis.read(b))>0){
                sb.append(new String(b,0,len,"UTF-8"));
            }

            return sb;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != bis)
                    bis.close();
                if(null != bos)
                    bos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
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
