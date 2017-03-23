package com.boyoi.core.utils.poi;

/**
 * 创建Excel的基础信息
 * @author Chenj
 */
public class ExcelBaseData {

    /**
     * 对应list的Url
     */
    private String listURI = null;
    /**
     * 创建的文件名称
     */
    private String fileName;

    /**
     * 头部对应名称, 一个sheet对应一个名称
     * 如不设置, 默认为sheet名称
     */
    private String[] headerName;

    /**
     * 表格名称
     * 可以创建多个相同格式的表格
     */
    private String[] sheetName;

    /**
     * 初始化
     * @param fileName 创建的文件名称
     * @param headerName 头部对应名称, 一个sheet对应一个名称, 如不设置, 默认为sheet名称
     * @param sheetName 表格名称,可以创建多个相同格式的表格
     */
    public ExcelBaseData(String fileName, String[] headerName, String[] sheetName) {
        this.fileName = fileName;
        this.headerName = headerName;
        this.sheetName = sheetName;
    }

    /**
     * 初始化
     * @param fileName 创建的文件名称
     * @param headerName 头部对应名称, 一个sheet对应一个名称, 如不设置, 默认为sheet名称
     * @param sheetName 表格名称,可以创建多个相同格式的表格
     */
    public ExcelBaseData(String fileName, String listURI, String[] headerName, String[] sheetName) {
        this.fileName = fileName;
        this.listURI = listURI;
        this.headerName = headerName;
        this.sheetName = sheetName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getListURI() {
        return listURI;
    }

    /**
     * 创建的文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHeaderName() {
        if (null != headerName)
            return headerName;
        else
            return sheetName;
    }

    /**
     * 头部对应名称, 一个sheet对应一个名称
     * 如不设置, 默认为sheet名称
     */
    public void setHeaderName(String... headerName) {
        this.headerName = headerName;
    }

    public String[] getSheetName() {
        return sheetName;
    }

    /**
     * 表格名称
     * 可以创建多个相同格式的表格
     */
    public void setSheetName(String... sheetName) {
        this.sheetName = sheetName;
    }
}
