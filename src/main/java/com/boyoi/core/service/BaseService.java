package com.boyoi.core.service;

import com.boyoi.core.domain.BaseDomain;

import java.util.List;

/**
 * 公共服务类
 *
 * @author Chenj
 */
public interface BaseService {

    /**
     * 通过实体查找精确查找有多少实体
     * @param t 实体
     * @return 集合
     */
    <T extends BaseDomain> List<T> findByDomain(T t);

}
