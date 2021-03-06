package com.boyoi.base.enums;

/**
 * 用户状态枚举
 * @author Chenj
 */
public enum UserStatusEnum {
    A("启用"),
    B("停用");

    private String itemValue;
    private String itemLabel;

    UserStatusEnum(String itemLabel){
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
