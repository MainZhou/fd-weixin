package com.boyoi.work.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.core.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录session对应RefreshKey
 * @author Chenj
 */
public class WxSessionRefreshKeyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxSessionRefreshKeyUtil.class);

    private Timer timer = new Timer("autoGetNewToken");

    private final String fileName = "WxSessionRefreshKeyUtil.data";
    private final String currClassFilePath = this.getClass().getResource("/").getPath() + File.separator + fileName;

    /**
     * key为cookie 名称为t的值
     * value为accessToken 和 refreshKey
     */
    private static Map<String, JSONObject> lastTime = new ConcurrentHashMap<>();

    private WxSessionRefreshKeyUtil() {}

    // 添加（更新）一个
    public void add(String cookie, JSONObject tokenObj){
        lastTime.put(cookie, tokenObj);
        serialize();
    }

    // 删除一个
    public void del(String cookie){
        lastTime.remove(cookie);
        serialize();
    }

    // 获得最新的access token
    public static String get(String cookie){
        if (null != cookie){
            JSONObject jsonObject = lastTime.get(cookie);
            if (null != jsonObject){
                Map<String,Object> data = JSON.parseObject(JSON.toJSONString(jsonObject.get("data")), HashMap.class);
                return (String)(data.get("access_token"));
            }
        }
        return null;
    }

    // 获得所有的access token
    public static Map<String, JSONObject> getAll(){
        return lastTime;
    }

    /**
     * Bean初始化时
     * 反序列化最后一次上传时间map到文件
     */
    public void start(){
        File file = new File(currClassFilePath);

        if (file.exists()){
            byte[] bytes = SerializeUtil.unserializeFile2Bytes(file);
            if (null != bytes){
                Map<String, JSONObject> result = SerializeUtil.unserialize(bytes);
                if (null != result){
                    lastTime.clear();
                    lastTime.putAll(result);
                    LOGGER.debug("反序列化cookie到内存！value:{}", result);
                }else {
                    throw new Error("反序列化文件失败!FilePath:"+file.getPath() + "!请检查!");
                }
            }
        }else {
            // 文件不存在。序列化
            serialize();
        }

        // 添加到定时更新token(每隔一天)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<String, JSONObject> map : lastTime.entrySet()){
                    JSONObject value = map.getValue();
                    String refresh_token = (String)value.get("refresh_token");
                    String url = WxUtils.baseUrl + "/oauth/token?grant_type=refresh_token&refresh_token="+refresh_token;
                    JSONObject obj = JSONObject.parseObject(HttpUtils.get(url));
                    if (null != obj){
                        map.setValue(obj);
                        LOGGER.debug("重新获得Token成功！");
                    }else {
                        LOGGER.error("重新获得Token失败！请检查！");
                    }
                }

                // 重新序列化
                serialize();
            }
        },30000, 24*60*60*1000);

    }

    /**
     * Bean销毁时
     * 序列化最后一次上传时间map到文件
     */
    public void serialize(){
        byte[] serialize = SerializeUtil.serialize(lastTime);

        if (null != serialize){
            BufferedOutputStream bos =null;
            File file = new File(currClassFilePath);
            try{
                // 不存在，创建文件
                if (!file.exists()){
                    boolean newFile = file.createNewFile();
                    if (!newFile){
                        throw new Error("不能创建文件"+file.getPath() + "!请检查!");
                    }
                }
                // 写入到文件中
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(serialize);
                bos.flush();
                bos.close();
                LOGGER.debug("序列化session对应RefreshKey到文件:{}中成功！", file.getPath());
            }catch (Exception e){
                e.printStackTrace();
                if (null != bos){
                    try {
                        bos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }else {
            throw new NullPointerException("序列化文件为空!请检查!");
        }
    }

}
