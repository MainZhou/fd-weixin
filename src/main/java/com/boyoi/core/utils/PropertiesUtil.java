package com.boyoi.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Properties配置文件操作帮助类
 *
 * @author Chenj
 */
public class PropertiesUtil {
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
    private Properties prop;

    /**
     * 读取classpath下面的文件
     */
    public PropertiesUtil(String fileNameInClassPath){
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileNameInClassPath);
            prop = new Properties();
            prop.load(is);
        }catch (Exception e){
            log.error("读取配置文件失败!"+fileNameInClassPath);
        }
    }

    /**
     * 读取为String
     * @param key key
     * @return String
     */
    public String readString(String key){
        if(null != prop){
            if (prop.containsKey(key)){
                return prop.getProperty(key);
            }
        }
        return null;
    }

    /**
     * 读取为String,如为空,返回默认值
     * @param key key
     * @return String
     */
    public String readString(String key,String defaultString){
        if(null != prop){
            if (prop.containsKey(key)){
                return prop.getProperty(key);
            }
        }
        return defaultString;
    }

    /**
     * 读取为Integer
     * @param key key
     * @return Integer
     */
    public Integer readInt(String key){
        if(null != prop){
            if (prop.containsKey(key)){
                return Integer.parseInt(prop.getProperty(key));
            }
        }
        return null;
    }

    /**
     * 读取为Integer,如为空,返回默认值
     * @param key key
     * @return Integer
     */
    public Integer readInt(String key,Integer defaultInt){
        if(null != prop){
            if (prop.containsKey(key)){
                return Integer.parseInt(prop.getProperty(key));
            }
        }
        return defaultInt;
    }

    /**
     * 读取为Long
     * @param key key
     * @return Long
     */
    public Long readLong(String key){
        if(null != prop) {
            if (prop.containsKey(key)) {
                return Long.parseLong(prop.getProperty(key));
            }
        }
        return null;
    }

    /**
     * 读取为Long,如为空,返回默认值
     * @param key key
     * @return Long
     */
    public Long readLong(String key,Long defaultLong){
        if(null != prop) {
            if (prop.containsKey(key)) {
                return Long.parseLong(prop.getProperty(key));
            }
        }
        return defaultLong;
    }
}
