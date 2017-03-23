package com.boyoi.core.service;

import com.boyoi.core.domain.ColumnDb;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

/**
 * 代码自动生成工具的Service
 * @author Chenj
 */

public interface CreatorService {

    /**
     * 获得所有的tables
     * @return 当前使用数据库的所有表
     */
    Collection<String> getTables();

    /**
     * 通过表名获得字段集合
     * @param tableName 表名
     * @return 字段集合Collection<Column>
     */
    Collection<ColumnDb> getColumnByTable(String tableName);

    /**
     * 处理提交的列属性
     * 生成 mybatis xml文件.Dao、service、controller、jsp、js文件
     * @return 生成后的消息集合 Boolean true为成功的集合  false 为失败的集合
     */
    Map<Boolean,Collection<String>> execColumnRequest(ColumnRequest columnRequest,HttpServletRequest servletRequest);

    /**
     * 获得当前连接的数据库类型
     * @return 数据库名称
     */
    String getDatabaseName();


}
