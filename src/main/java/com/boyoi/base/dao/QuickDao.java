package com.boyoi.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 快捷方式 Dao层
 *
 * @author Chenj
 */
public interface QuickDao {
    /**
     * 获得当前用户的快捷方式的数量
     * @param userId 用户GUID
     * @return 当前用户的快捷方式的数量
     */
    List<Map<String, String>> getQuickNum(String userId);
}