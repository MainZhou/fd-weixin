package com.boyoi.core.enums;

/**
 * 文件是否允许被上传枚举
 * @author Chenj
 */
public enum FileTypeEnum {
    MEDIA("mp3", "wav", "wma","wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"),//音视频
    FILE("zip", "rar", "pdf"),//文件文档
    WORD("doc", "docx", "xls", "xlsx", "ppt"),//office文档
    PIC("jpg", "jpeg", "png", "bmp", "gif");//图片

    private String[] types;

    private FileTypeEnum(String... types){
        this.types = types;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
