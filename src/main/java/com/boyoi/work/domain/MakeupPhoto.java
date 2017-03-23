package com.boyoi.work.domain;

/**
 * 进货明细图片
 * User: zhoujl
 * Date: 2017/1/16
 * Time: 11:26
 */
public class MakeupPhoto {
    /**
     * 证照类型
     */
    private String documentType;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 检测任务编号
     */
    private String detectTaskId;
    /**
     * 证照编号
     */
    private String guid;
    /**
     * 老的证照路径
     */
    private String oldPath;

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDetectTaskId(String detectTaskId) {
        this.detectTaskId = detectTaskId;
    }

    public String getDocumentType() {

        return documentType;
    }

    public String getPath() {
        return path;
    }

    public String getDetectTaskId() {
        return detectTaskId;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setOldPath(String oldPath) {
        this.oldPath = oldPath;
    }

    public String getGuid() {

        return guid;
    }

    public String getOldPath() {
        return oldPath;
    }
}
