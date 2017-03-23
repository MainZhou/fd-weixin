package com.boyoi.base.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于存放某IP登录失败次数的帮助类
 * @author Chenj
 */
public class LoginFailHelper {

    private static final Logger LOG = LoggerFactory.getLogger(LoginFailHelper.class);

    private static Map<String, Integer> loginFailMap = new ConcurrentHashMap<>();
    private static Timer timer = new Timer("cleanLoginFailMap");

    // 每隔一天清空次数
    static {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loginFailMap.clear();
                LOG.debug("清空登录失败缓存成功!");
            }
        }, 86400000 , 86400000);
    }

    /**
     * 添加一次登录失败记录
     * @param ip ip地址
     */
    public static void addOneTimes(String ip){
        Integer times = loginFailMap.get(ip);
        if (null == times)
            loginFailMap.put(ip, 1);
        else
            loginFailMap.put(ip, times + 1);
    }

    /**
     * 删除用户登录失败记录
     * @param ip ip地址
     */
    public static void deleteTimes(String ip){
        loginFailMap.put(ip, 0);
    }

    /**
     * 获得登录失败次数
     * @param ip ip地址
     */
    public static int getTimes(String ip){
        Integer times = loginFailMap.get(ip);
        return  null == times ? 0 : times;
    }

}
