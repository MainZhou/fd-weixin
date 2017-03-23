package com.boyoi.core.domain;

import java.io.Serializable;
/**
 * 代码自动生成工具之
 * 数据库中取出的字段
 * @author Chenj
 */
public class ColumnDb implements Serializable {
    /**
	 *@Function:
	 *@Time:2016年4月7日上午9:36:39
	 *@Author:湛渝
	 *@Remark:
	 */
	private static final long serialVersionUID = 3842078917856847710L;
	/**
     * 字段名称(java)
     */
    private String javaName;
    /**
     * 字段名称(In Db)
     */
    private String dbName;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 字段长度
     */
    private String size;

    /**
     * 字段中文名字,从百度翻译获取
     */
    private String cnName;

    public ColumnDb(String javaName, String dbName, String type, String size) {
        this.javaName = javaName;
        this.dbName = dbName;
        this.type = type;
        this.size = size;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }
}
