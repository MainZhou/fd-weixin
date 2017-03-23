package com.boyoi.core.domain;

import com.boyoi.core.creator.InputTypeEnum;

import java.io.Serializable;

/**
 * 代码自动生成工具之
 * 列属性
 *
 * @author Chenj
 */
public class ColumnAttr implements Serializable {
    /**
	 *@Function:
	 *@Time:2016年4月7日上午9:36:34
	 *@Author:湛渝
	 *@Remark:
	 */
	private static final long serialVersionUID = 670548051163080473L;

	/**
     * 列的java类型
     */
    private String javaType;

    /**
     * 字段名称(In java)
     */
    private String javaName;

    /**
     * 字段名称(In Db)
     */
    private String dbName;

    /**
     * 中文标签
     */
    private String cnLabel;

    /**
     * 添加编辑
     */
    private Boolean addEdit;

    /**
     * 输入方式
     */
    private InputTypeEnum inputType;

    /**
     * 输入方式,转化为js验证的字符
     */
    @SuppressWarnings("unused")
	private String inputTypeString;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 长度限制
     */
    private Integer length;

    /**
     * 列表显示
     */
    private Boolean display;

    /**
     * 是否排序
     */
    private Boolean sort;

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCnLabel() {
        return cnLabel;
    }

    public void setCnLabel(String cnLabel) {
        this.cnLabel = cnLabel;
    }

    public Boolean getAddEdit() {
        return addEdit;
    }

    public void setAddEdit(Boolean addEdit) {
        this.addEdit = addEdit;
    }

    public InputTypeEnum getInputType() {
        return inputType;
    }

    public String getInputTypeString() {
        switch (inputType.name()){
            case "常规字符串":
                return "length[~" + length + "]";
            case "整数":
                return "digits;range[0~" + length + "]";
            case "日期":
                return "date";
            case "浮点数":
                return "float";
            case "身份证号":
                return "IDcard";
            case "座机":
                return "tel";
            case "手机":
                return "mobile";
            case "汉字":
                return "chinese";
            case "英文字母":
                return "letters";
            case "网址":
                return "url";
            case "邮箱":
                return "email";
            default:
                return "length[~" + length + "]";
        }
    }

    public void setInputType(InputTypeEnum inputType) {
        this.inputType = inputType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }
}
