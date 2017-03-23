package com.boyoi.core.utils.poi;

import java.util.List;
import java.util.Map;

/**
 * 封装从数据库查询的数据
 * @author Chenj
 */
public class ExcelQueryData {

    /**
     *  sheet表名称
     */
    private String sheetName;

    /**
     * sheet 对应的数据集
     * list代表多少行
     * map代表sql查询出来的列名与值的对应关系
     */
    private List<Map<String, Object>> data;

    public ExcelQueryData(String sheetName, List<Map<String, Object>> data) {
        this.sheetName = sheetName;
        this.data = data;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
