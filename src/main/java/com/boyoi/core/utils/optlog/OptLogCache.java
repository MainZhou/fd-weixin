package com.boyoi.core.utils.optlog;

import com.boyoi.base.domain.LogOpt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志缓存
 * @author Chenj
 */
public class OptLogCache {

    private static Map<String, LogOpt> optLogCache = new ConcurrentHashMap<>();

    // 防止实例化
    private OptLogCache() {
    }

    public static Map<String, LogOpt> getOptLogCache() {
        return optLogCache;
    }
}
