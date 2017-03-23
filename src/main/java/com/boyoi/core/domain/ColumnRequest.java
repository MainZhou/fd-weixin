package com.boyoi.core.domain;

import javax.validation.constraints.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 代码自动生成工具之
 * 封装提交的列属性
 *
 * @author Chenj
 */
public class ColumnRequest {
    /**
     *  表名
     */
    private String tableName;

    /**
     * 生成文件的绝对路径
     */
    private String absPath;

    /**
     * 所属菜单ID,即上级菜单
     */
    private String parentMenuId;

    /**
     * 是否自动生成模块和菜单到数据中
     */
    private Boolean gen_module_menu;

    /**
     * 什么数据库? 生成sql时根据不同的条件生成不同的sql,如批量添加
     */
    private String databaseName;

    /**
     * 功能名称
     */
    private String funcName;

    /**
     * 开发人员
     */
    private String devPerson;

    /**
     * 包名
     */
    @NotNull
    private String packageName;

    /**
     * 类名前缀
     */
    private String prefix;

    /**
     * 类名前缀(第一个字母小写)
     */
    @SuppressWarnings("unused")
	private String prefixFirstCharLow;

    /**
     * 覆盖生成
     */
    private Boolean cover;

    /**
     * 普通搜索字段
     */
    private String normalSearchColumn;

    /**
     * 生成的类型controller层 service层 dao层 jsp
     */
    private Map<String,Boolean> genType = new LinkedHashMap<>();

    /**
     * 封装列的属性.String 代表列名. ColumnAttr为列的属性
     */
    private Map<Integer,ColumnAttr> attrs = new LinkedHashMap<>();


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Boolean getGen_module_menu() {
        return gen_module_menu;
    }

    public void setGen_module_menu(Boolean gen_module_menu) {
        this.gen_module_menu = gen_module_menu;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getDevPerson() {
        return devPerson;
    }

    public void setDevPerson(String devPerson) {
        this.devPerson = devPerson;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixFirstCharLow() {
        if (null != prefix && prefix.length()>0){
            return String.valueOf(prefix.charAt(0)).toLowerCase() + prefix.substring(1,prefix.length());
        }
        return null;
    }

    public Boolean getCover() {
        return cover;
    }

    public void setCover(Boolean cover) {
        this.cover = cover;
    }

    public Map<String, Boolean> getGenType() {
        return genType;
    }

    public void setGenType(Map<String, Boolean> genType) {
        this.genType = genType;
    }

    public String getNormalSearchColumn() {
        return normalSearchColumn;
    }

    public void setNormalSearchColumn(String normalSearchColumn) {
        this.normalSearchColumn = normalSearchColumn;
    }

    public Map<Integer, ColumnAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, ColumnAttr> attrs) {
        this.attrs = attrs;
    }
}
