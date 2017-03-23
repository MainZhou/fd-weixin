package com.boyoi.work.utils;

import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.enums.CommonExceptionEnum;
import com.boyoi.core.exception.CommonException;
import com.boyoi.core.exception.MsgException;
import com.boyoi.core.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存用户信息
 * @author Chenj
 */
public class WxUserInfoSaveUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxUserInfoSaveUtil.class);

    private final String fileName = "WxUserInfoSaveUtil.data";
    private final String currClassFilePath = this.getClass().getResource("/").getPath() + File.separator + fileName;

    /**
     * key为cookie 名称为t的值
     * value为userInfo
     */
    private static Map<String, JSONObject> lastTime = new ConcurrentHashMap<>();

    private WxUserInfoSaveUtil() {}

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

    // 获得用户信息
    public static JSONObject get(String cookie){
        return lastTime.get(cookie);
    }

    // 获得用户信息
    public static JSONObject get(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (null != cookies){
            for (Cookie cookie : cookies){
                if ("t".equals(cookie.getName())){
                    return lastTime.get(cookie.getValue());
                }
            }
        }
        throw new CommonException(CommonExceptionEnum.E2);
    }

    /**
     * Bean初始化时
     * 反序列化最后一次上传时间map到文件
     */
    public void unSerialize(){
        File file = new File(currClassFilePath);

        if (file.exists()){
            byte[] bytes = SerializeUtil.unserializeFile2Bytes(file);
            if (null != bytes){
                Map<String, JSONObject> result = SerializeUtil.unserialize(bytes);
                if (null != result){
                    lastTime.clear();
                    lastTime.putAll(result);
                    LOGGER.debug("反序列化用户信息到内存成功！value:{}", result);
                }else {
                    throw new Error("反序列化文件失败!FilePath:"+file.getPath() + "!请检查!");
                }
            }
        }else {
            // 文件不存在。序列化
            serialize();
        }
    }

    /**
     * 序列化数据到文件
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
                LOGGER.debug("序列化用户信息到文件:{}中成功！", file.getPath());
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
