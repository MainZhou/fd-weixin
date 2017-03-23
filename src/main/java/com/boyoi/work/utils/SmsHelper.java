package com.boyoi.work.utils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SMS帮助类
 * @author Chenj
 */
public class SmsHelper {
    /**
     * 存放上次发送短信的sessionId与时间map
     */
    public static Map<String, Date> lastSendSms = new ConcurrentHashMap<>();
}
