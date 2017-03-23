package com.boyoi.base.service;

import java.util.Map;

/**
 * 快捷方式 service层
 *
 * @author Chenj
 */
public interface QuickService{
    /**
     * 获得当前用户的快捷方式内容的数量
     * @param userId 用户GUID
     * @return 当前用户的快捷方式的数量
     */
    Map<String, String> getQuickNum(String userId);
}