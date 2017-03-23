package com.boyoi.base.service;

import com.boyoi.base.domain.ColumnCustom;
import com.boyoi.core.service.BaseService;

/**
 * 列配置 service层
 *
 * @author Chenj
 */
public interface ColumnCustomService extends BaseService {

    /**
    * 添加或更新列配置
    * @param columnCustom 列配置 实体
    * @return 受影响的个数
    */
    int addOrUpdate(ColumnCustom columnCustom);

    /**
     * 查找当前用户某domainUrl下的列习惯
     * @param userId 用户id
     * @param domainUrl 实体url
     * @return ColumnCustom
     */
    ColumnCustom findCurrColumnCustom(String userId, String domainUrl);

    /**
     * 清除用户的列习惯
     * @param userId 用户id
     * @param domainUrl 实体url
     * @return 是否成功
     */
    int del(String userId, String domainUrl);
}