package com.boyoi.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * JDBC原生操作数据库的帮助类
 *
 * @author Chenj
 */
public class JdbcUtil {
    /**
     * 获得数据库Connection
     * @return Connection
     */
    public static Connection getConn(){
        PropertiesUtil propUtil = new PropertiesUtil("/jdbc.properties");
        try {
            return DriverManager.getConnection(propUtil.readString("jdbc.url","jdbc\\:oracle\\:thin\\:@192.168.1.19\\:1521\\:orcl"),
                    propUtil.readString("jdbc.username","lng"),
                    propUtil.readString("jdbc.password","lng"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放连接
     * @param rs ResultSet
     * @param st Statement
     * @param conn Connection
     */
    public static void close(ResultSet rs,Statement st ,Connection conn){
        if(null != rs){
            try {
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
                rs = null;
            }
        }

        if(null != st){
            try {
                st.close();
            }catch (Exception e){
                e.printStackTrace();
                st = null;
            }
        }

        if(null != conn){
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                    conn = null;
                }
            }
        }

    }

    /**
     * 处理字段名称(前二个字母转为小写.其它不变)
     * @param columnName 字段名称
     * @return 处理后的
     */
    public static String handleColumnName(String columnName){
        if (columnName != null && !"".equals(columnName.trim())) {
            String[] split = columnName.split("_");
//            分成了两个，说明是用_分开的
            if (split.length >= 2){
                String tmp = "";
                // 迭代每个字符，第一个字符为大写,后面的小写
                for (int i = 0; i< split.length; i++){
                    if (split[i].length() > 1){
                        // 生成java驼峰的写法
                        if (i == 0)
                            tmp += String.valueOf(split[i].charAt(0)).toLowerCase() +
                                    split[i].substring(1, split[i].length()).toLowerCase();

                        else
                            tmp += String.valueOf(split[i].charAt(0)).toUpperCase() +
                                    split[i].substring(1, split[i].length()).toLowerCase();
                    }
                }
                return tmp;
            }
            // 只有一个字母 userName
            else {
                columnName = columnName.trim();
                // 如果全是大写，说明是Oracle, 全部转为小写
                if (columnName.matches("[A-Z|_]+")){
                    return columnName.toLowerCase();
                }else {
                    // 其它数据库。如mysql. 前两个小写，其它不变
                    return columnName.substring(0, 2).toLowerCase() + columnName.substring(2);
                }
            }

        }
        return columnName;
    }

    /**
     * 数据库类型转java类型
     * @param dbType 数据库类型
     * @return java类型
     */
    public static String dbTypeToJavaType (String dbType){
        if (dbType == null) {
            dbType = "";
        }
        switch (dbType){
            case "uniqueidentifier":
                return "String";
            case "varchar":
                return "String";
            case "char":
                return "String";
            case "datetime":
                return "java.util.Date";
            case "date":
                return "java.util.Date";
            case "int":
                return "Integer";
            case "number":
                return "Integer";
            case "numeric":
                return "Double";
            case "float":
                return "Double";
            case "tinyint":
                return "Integer";
            case "bigint":
                return "Long";
            case "decimal":
                return "Double";
            case "bit":
                return "Integer";
            default:
                return "String";
        }

    }

}
