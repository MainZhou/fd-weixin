package com.boyoi.core.dao.impl;

import com.boyoi.core.dao.CreatorDao;
import com.boyoi.core.domain.ColumnDb;
import com.boyoi.core.utils.JdbcUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 代码自动生成工具
 * dao实现类
 * @author Chenj
 */
@Repository
public class CreateDaoImpl implements CreatorDao {

    @Override
    public Collection<String> getTables() {
        // 数据用户名
        String username = null;
        // 数据库的原信息
        DatabaseMetaData dmd;
        String[] types = {"TABLE"};

        try {
            Collection<String> coll = new ArrayList<>();
            Connection conn = JdbcUtil.getConn();
            dmd = conn.getMetaData();
            if ( "Oracle".equalsIgnoreCase(dmd.getDatabaseProductName())){
                username = dmd.getUserName();
            }
            conn.getMetaData();
            //获得所有的table
            ResultSet rs = conn.getMetaData().getTables(null, username, null, types);

            //取出TABLE_NAME
            while (rs.next()) {
                coll.add(rs.getString("TABLE_NAME"));
            }

            //关闭连接
            JdbcUtil.close(rs,null,conn);
            return coll;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<ColumnDb> getColumnByTable(String tableName) {
        try {
            Collection<ColumnDb> coll = new ArrayList<>();
            Connection conn = JdbcUtil.getConn();
            //获得字段
            ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null);

            //迭代字段
            while (rs.next()){
                String columnName = rs.getString("COLUMN_NAME");//字段名称
                String columnType = rs.getString("TYPE_NAME");//字段类型
                String columnSize = rs.getString("COLUMN_SIZE");//字段长度
                if(null != columnName && !"".equals(columnName.trim()) && null != columnType){
                    coll.add(new ColumnDb(JdbcUtil.handleColumnName(columnName),
                                          columnName,
                                          JdbcUtil.dbTypeToJavaType(columnType.toLowerCase()),
                                          columnSize)
                            );
                }
            }

            //关资源
            JdbcUtil.close(rs,null,conn);
            return coll;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDatabaseName() {
        Connection conn = JdbcUtil.getConn();
        try {
            return conn.getMetaData().getDatabaseProductName();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.close(null, null, conn);
        }
        return "";
    }

}
