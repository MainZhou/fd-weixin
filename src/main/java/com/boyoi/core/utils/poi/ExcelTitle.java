package com.boyoi.core.utils.poi;


/**
 * Excel 标题, 对应列名等
 * @author Chenj
 */
public class ExcelTitle {

    /**
     * 是否显示
     */
    private Boolean display;

    /**
     * 是否为固定列(默认false)
     */
    private Boolean frozen = false;

    /**
     * 起始行, 默认第2行, 0开始
     */
    private Integer startRow = 1;

    /**
     * 对应select查询出来的列名
     */
    private String columnName;

    /**
     * Grid对应field字段名称
     */
    private String columnNameInGrid;

    /**
     * 标题名称
     */
    private String[] titleNames;

    /**
     * 自定义样式
     */
    private POICustomStyle poiCustomStyle;

    /**
     * 单元格宽
     */
    private Integer cellWidth;


    /**
     * 设置头部名称和列宽度, 起始行
     * @param startRow 开始行
     * @param columnName 对应select查询出来的列名
     * @param cellWidth 列宽度,多少个字一般就写多少
     * @param titleNames 标题名称
     */
    public ExcelTitle(Integer startRow, String columnName, Integer cellWidth, String... titleNames) {
        this.startRow = startRow;
        this.columnName = columnName;
        this.titleNames = titleNames;
        this.cellWidth = cellWidth;
        // 默认columnName
        this.columnNameInGrid = columnName;
    }

    /**
     * 设置头部名称和列宽度, 默认从第二行开始
     * @param cellWidth 列宽度,多少个字一般就写多少
     * @param columnName 对应select查询出来的列名
     * @param titleNames 标题名称
     */
    public ExcelTitle(Integer cellWidth, String columnName, String... titleNames) {
        this.columnName = columnName;
        this.titleNames = titleNames;
        this.cellWidth = cellWidth;
        // 默认columnName
        this.columnNameInGrid = columnName;
    }

    /**
     * 带参的构造函数
     * 设置头部名称和列宽度, 默认从第二行开始
     * @param cellWidth 列宽度,多少个字一般就写多少
     * @param columnName 对应select查询出来的列名
     * @param poiCustomStyle 自定义样式
     * @param titleNames 标题名称
     */
    public ExcelTitle(Integer cellWidth, String columnName,POICustomStyle poiCustomStyle, String... titleNames) {
        this.columnName = columnName;
        this.titleNames = titleNames;
        this.poiCustomStyle = poiCustomStyle;//poiCustomStyle 自定义样式
        this.cellWidth = cellWidth;
        // 默认columnName
        this.columnNameInGrid = columnName;
    }

    public String getColumnNameInGrid() {
        return columnNameInGrid;
    }

    public ExcelTitle setColumnNameInGrid(String columnNameInGrid) {
        this.columnNameInGrid = columnNameInGrid;
        return this;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public ExcelTitle setFrozen(Boolean frozen) {
        this.frozen = frozen;
        return this;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String[] getTitleNames() {
        return titleNames;
    }

    public void setTitleNames(String[] titleNames) {
        this.titleNames = titleNames;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }

    public POICustomStyle getPoiCustomStyle() {//get方法
        return poiCustomStyle;
    }

    public void setPoiCustomStyle(POICustomStyle poiCustomStyle) {//set方法
        this.poiCustomStyle = poiCustomStyle;
    }
}
