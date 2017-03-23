package com.boyoi.core.dao;

import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.utils.poi.ExcelExportRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * dao层基类
 * @author Chenj
 */
@SuppressWarnings("unused")
public interface BaseDao {

    /**
     * 通过实体查找精确查找有多少实体
     * @param t 实体
     * @return 集合
     */
    <T extends BaseDomain> List<T> findByDomain(T t);
}
