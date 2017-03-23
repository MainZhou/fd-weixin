package com.boyoi.core.dao;

import com.boyoi.core.domain.ColumnDb;

import java.util.Collection;

/**
 * 代码自动生成工具的Dao
 * @author Chenj
 */
public interface CreatorDao {
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
     * 获得当前连接的数据库类型
     * @return 数据库名称
     */
    String getDatabaseName();
}
