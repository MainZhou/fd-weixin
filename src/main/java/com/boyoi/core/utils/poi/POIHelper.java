package com.boyoi.core.utils.poi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.boyoi.base.domain.ColumnCustom;
import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.service.ColumnCustomService;
import com.boyoi.core.common.SpringTool;
import com.boyoi.core.exception.MsgException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POI 帮助类
 * @author Chenj
 */
public class POIHelper {

    private static final Logger LOG = LoggerFactory.getLogger(POIHelper.class);
    // 注入自定义列service
    private ColumnCustomService columnCustomService;

    private ExcelBaseData excelBaseData;
    private List<CellRangeAddress> cellRangeAddressList;
    private List<ExcelTitle> excelTitles;
    // 查询出的数据
    private List<ExcelQueryData> excelQueryData;

    // 头部对应column与列的对应关系
    private Map<String, Integer> columnCellRelation = new HashMap<>();

    // 头部对应column与列的样式关系
    private Map<String, CellStyle> columnCellStyleRelation = new HashMap<>();

    // 创建HSSFWorkbook
    private HSSFWorkbook workbook = new HSSFWorkbook();
    private CellStyle contentCellStyle = POIUtil.contentCellStyle(workbook);
    private CellStyle titleCellStyle = POIUtil.titleCellStyle(workbook);
    private CellStyle headerCellStyle = POIUtil.headerCellStyle(workbook);

    /**
     * 初始化数据
     * @param excelBaseData 基础信息
     * @param cellRangeAddressList 合并区域
     * @param excelTitles 标题
     * @param excelQueryData 查询出的数据
     */
    public POIHelper(ExcelBaseData excelBaseData,
                     List<CellRangeAddress> cellRangeAddressList,
                     List<ExcelTitle> excelTitles,
                     List<ExcelQueryData> excelQueryData) {
        // 初始化service
        columnCustomService = (ColumnCustomService) SpringTool.getBean("columnCustomServiceImpl");

        this.excelBaseData = excelBaseData;
        this.cellRangeAddressList = cellRangeAddressList;
        this.excelTitles = excelTitles;
        this.excelQueryData = excelQueryData;
    }


    /**
     * 生成数据
     * @return workbook
     */
    public Workbook make(){

        // 获得自定义列配置
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute(LoginFlag.LOGIN_SUCCESS.name());
        // 获得通用list地址
        String listURI;
        if (null == excelBaseData.getListURI()){
            listURI = request.getRequestURI().replace(request.getContextPath(), "");
            String mainURI = getMainRequestURI(listURI);
            if (null != mainURI){
                listURI = mainURI + "list";
            }else {
                throw new MsgException("导出的URL不符合规范！如：/customInfo/excelExport！当前请求的是：" + listURI);
            }
        }else{
            // 获得自定义URI
            listURI = excelBaseData.getListURI();
        }

        // 获得列
        ColumnCustom currColumnCustom = columnCustomService.findCurrColumnCustom(user.getGuid(), listURI);

        if (null != currColumnCustom){

            String columnConf = currColumnCustom.getColumnConf();

            // 列是否显示
            if (null != columnConf && !"".equals(columnConf)){
                JSONObject columnConfJSON = JSON.parseObject(columnConf);
                for (ExcelTitle excelTitle : excelTitles){
                    Object o = columnConfJSON.get(excelTitle.getColumnNameInGrid());
                    if (null != o){
                        // 不为空。设置为是否显示
                        excelTitle.setDisplay((Boolean)o);
                    }
                }
            }

            // 列顺序
            String columnOrder = currColumnCustom.getColumnOrder();
            if (null != columnOrder && !"".equals(columnOrder)){
                // 新的excelTitle
                List<ExcelTitle> newExcelTitle = new ArrayList<>(excelTitles.size());

                JSONObject columnOrderJSON = JSON.parseObject(columnOrder, Feature.OrderedField); // 解析为LinkedHashMap

                // 非固定列。根据自定义列来排序
                for (ExcelTitle excelTitle : excelTitles) {
                    // 是固定列。就加在前面
                    if (excelTitle.getFrozen()) {
                        newExcelTitle.add(excelTitle);
                    }
                }

                // 有顺序的列。根据顺序依次排列
                for (Map.Entry<String, Object> entry : columnOrderJSON.entrySet()){

                    for (ExcelTitle excelTitle : excelTitles){

                        // 非固定列。根据顺序向后面加
                        if (excelTitle.getColumnNameInGrid().equals(entry.getKey())){
                            // 列的宽度为null 设置为用户自定义宽度
                            excelTitle.setCellWidth((Integer) entry.getValue() / 6);

                            // 添加到新列中
                            newExcelTitle.add(excelTitle);
                            break;
                        }
                    }

                }

                // 没有顺序的列， 插入到上一个对应的位置
                for (int i=0; i<excelTitles.size(); i++){
                    if (!newExcelTitle.contains(excelTitles.get(i))){
                        if(i-1<0){
                            // 小于0，加到最前面
                            newExcelTitle.add(0, excelTitles.get(i));
                        }else{
                            newExcelTitle.add(newExcelTitle.indexOf(excelTitles.get(i-1)) + 1, excelTitles.get(i));
                        }
                    }
                }

                // 设为新的顺序
                excelTitles = newExcelTitle;
            }

        }else {
            LOG.warn("没有找到当前用户的自定义列！将默认开发自定义的列顺序！");
        }

        /**
         * 创建sheet和头部、标题
         * 迭代所有的sheet, 创建不同的sheet对象
         */
        for (int k = 0; k < excelBaseData.getSheetName().length; k++){
            HSSFSheet sheet = workbook.createSheet(excelBaseData.getSheetName()[k]);

            // 处理合并区域
            for (CellRangeAddress address : cellRangeAddressList){
                sheet.addMergedRegion(address);
            }

            /**
             * 处理头部
             */
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(34);
            HSSFCell headerCell = headerRow.createCell(0);
            headerCell.setCellValue(excelBaseData.getHeaderName()[k]);
            headerCell.setCellStyle(headerCellStyle);

            /**
             * 处理标题
             */
            for (int i = 0; i < excelTitles.size(); i++){
                ExcelTitle excelTitle = excelTitles.get(i);
                int startRow = excelTitle.getStartRow();

                // 设置column cell 对应关系
                columnCellRelation.put(excelTitle.getColumnName(), i);
                if (null != excelTitle.getPoiCustomStyle()){
                    columnCellStyleRelation.put(excelTitle.getColumnName(), POIUtil.customCellStyle(workbook, excelTitle.getPoiCustomStyle()));
                }

                // 设置列宽（1个字符占256）
                sheet.setColumnWidth(i, excelTitle.getCellWidth() * 256);

                // 迭代每一列头部的名称，根据名称多少，创建多少行
                for (int j =0; j < excelTitle.getTitleNames().length; j++){
                    // 不存在row, 就创建
                    HSSFRow row = sheet.getRow(startRow + j);
                    if (null == row){
                        row = sheet.createRow(startRow + j);
                        row.setHeightInPoints(22);
                    }

                    // 设置值
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(excelTitle.getTitleNames()[j]);
                    cell.setCellStyle(titleCellStyle);
                    // 设置是否显示
                    if (null != excelTitle.getDisplay()){
                        sheet.setColumnHidden(i, !excelTitle.getDisplay());
                    }
                }
            }
        }

        /**
         * 处理数据区域
         * 迭代查询出的数据, 根据对应上的sheet名称, 写入对应的数据
         */
        for (ExcelQueryData queryData : excelQueryData){
            HSSFSheet sheet = workbook.getSheet(queryData.getSheetName());
            if (null != sheet){
                // 迭代row,一个data就是一行
                int currRow = sheet.getLastRowNum() + 1;
                for (Map<String, Object> data : queryData.getData()) {
                    HSSFRow row = sheet.createRow(currRow);
                    row.setHeightInPoints(17);
                    // 迭代columnCellRelation, 找到对应的列, 在当前行创建列名
                    for (Map.Entry<String, Integer> map : columnCellRelation.entrySet()){
                        Integer columnNum =  map.getValue();
                        HSSFCell cell = row.createCell(columnNum);
                        POIUtil.setCellValue(cell, data.get(map.getKey()));
                        // 设置自定义样式
                        CellStyle cellStyle = columnCellStyleRelation.get(map.getKey());
                        //判断传入的自定义样式是否为空
                        if (null != cellStyle){//不为空则是显示自定义样式
                            cell.setCellStyle(cellStyle);
                        }else {
                            // 为空默认设置为通用样式
                            cell.setCellStyle(contentCellStyle);
                        }
                        // 设置格式为字符,导出为文本格式
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    // 继续下一行
                    currRow +=1;
                }
            }
        }

        return workbook;
    }

    /**
     * 获得主要目录。如/columnRequest/excelExport 返回/columnRequest/
     * @param requestURI /columnRequest/excelExport
     * @return 主要目录
     */
    private String getMainRequestURI(String requestURI) {
        int total = 0;
        for (int i=0;i<requestURI.length();i++){
            if (total == 2){
                 return requestURI.substring(0, i);
            }
            if (requestURI.charAt(i) == '/'){
                total ++;
            }
        }
        return null;
    }

    public ExcelBaseData getExcelBaseData() {
        return excelBaseData;
    }

    public void setExcelBaseData(ExcelBaseData excelBaseData) {
        this.excelBaseData = excelBaseData;
    }

    public List<CellRangeAddress> getCellRangeAddressList() {
        return cellRangeAddressList;
    }

    public void setCellRangeAddressList(List<CellRangeAddress> cellRangeAddressList) {
        this.cellRangeAddressList = cellRangeAddressList;
    }

    public List<ExcelTitle> getExcelTitles() {
        return excelTitles;
    }

    public void setExcelTitles(List<ExcelTitle> excelTitles) {
        this.excelTitles = excelTitles;
    }

    public List<ExcelQueryData> getExcelQueryData() {
        return excelQueryData;
    }

    public void setExcelQueryData(List<ExcelQueryData> excelQueryData) {
        this.excelQueryData = excelQueryData;
    }

    public Map<String, Integer> getColumnCellRelation() {
        return columnCellRelation;
    }

    public void setColumnCellRelation(Map<String, Integer> columnCellRelation) {
        this.columnCellRelation = columnCellRelation;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle getContentCellStyle() {
        return contentCellStyle;
    }

    public void setContentCellStyle(CellStyle contentCellStyle) {
        this.contentCellStyle = contentCellStyle;
    }

    public CellStyle getTitleCellStyle() {
        return titleCellStyle;
    }

    public void setTitleCellStyle(CellStyle titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
    }

    public CellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }

    public void setHeaderCellStyle(CellStyle headerCellStyle) {
        this.headerCellStyle = headerCellStyle;
    }
}
