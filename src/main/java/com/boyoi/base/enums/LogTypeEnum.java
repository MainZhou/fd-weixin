package com.boyoi.base.enums;

/**
 * 操作日志类型
 * @author Chenj
 */
public enum LogTypeEnum {
    A("添加"),
    U("修改"),
    D("删除");

    private String itemValue;
    private String itemLabel;

    LogTypeEnum(String itemLabel){
        this.itemValue = this.name();
        this.itemLabel = itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }


    @Override
    public String toString() {
        return this.itemLabel;
    }
}
