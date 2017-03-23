package com.boyoi.base.enums;

/**
 * 部门状态枚举
 * @author Chenj
 */
public enum DepartmentStatusEnum {
    A("启用"),
    B("停用");

    private String itemValue;
    private String itemLabel;

    DepartmentStatusEnum(String itemLabel){
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
