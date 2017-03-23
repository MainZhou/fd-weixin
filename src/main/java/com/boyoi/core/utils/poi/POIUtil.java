package com.boyoi.core.utils.poi;

import com.boyoi.core.utils.DateTimeUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * POI的工具类
 * @author Chenj
 */
public class POIUtil {

    /**
     * 根据value不同的数据类型设置Cell的值
     * @param cell cell对象
     * @param value 值
     */
    public static void setCellValue(HSSFCell cell, Object value){
        if (null == value)
            cell.setCellValue("");
        else if (value instanceof String )
            cell.setCellValue((String)value);
        else if (value instanceof Boolean)
            cell.setCellValue((Boolean)value);
        else if (value instanceof Number)
            /*cell.setCellValue(Double.valueOf(String.valueOf(value)));*/
        	cell.setCellValue(String.valueOf(value));
        else if (value instanceof Timestamp)
            cell.setCellValue(DateTimeUtil.yyyyMMddhhmmss(new Date(((Timestamp) value).getTime())));
        else if (value instanceof java.sql.Date)
            cell.setCellValue(DateTimeUtil.yyyyMMdd(new Date(((java.sql.Date) value).getTime())));
    }

    /**
     * 把excel发送给浏览器
     * @param excelBaseData 基础数据
     * @param workbook workbook
     * @param response HttpServletResponse
     */
    public static void excel2Browser(ExcelBaseData excelBaseData, Workbook workbook, HttpServletResponse response){
        ServletOutputStream outputStream;
        try {
            /**
             * 写入到浏览器中
             */
            String fileName = new String(excelBaseData.getFileName().getBytes("utf-8"), "ISO-8859-1");
            response.setHeader("content-disposition", "attachment; filename=" + fileName + ".xls");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);

            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 标题的cell样式
     * @param wb workbook对象
     * @return CellStyle
     */
    public static CellStyle headerCellStyle(Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();

        // 居中对齐
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        /**
         * 字体设置
         */
        org.apache.poi.ss.usermodel.Font font = wb.createFont();
        font.setBold(true);
        // 字体大小
        font.setFontHeightInPoints((short)18);
        cellStyle.setFont(font);

        return cellStyle;
    }

    /**
     * 标题的cell样式
     * @param wb workbook对象
     * @return CellStyle
     */
    public static CellStyle titleCellStyle(Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();

        // 居中对齐
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        /**
         * 字体设置
         */
        org.apache.poi.ss.usermodel.Font font = wb.createFont();
        font.setColor(HSSFColor.LIGHT_BLUE.index);
        font.setBold(true);
        // 字体大小
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);

        // 边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);

        // 前景色
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        // 后景色
//        cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        // 添充规则
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


        return cellStyle;
    }

    /**
     * 表格内容样式
     * @param wb workbook对象
     * @return CellStyle
     */
    public static CellStyle contentCellStyle(Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();

        // 居左对齐
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 自动换行
        cellStyle.setWrapText(true);

        // 边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);

        return cellStyle;
    }

    /**
     * 创建自定义样式
     * @param wb workbook对象
     * @param poiCustomStyle 自定义样式枚举
     * @return CellStyle
     */
    public static CellStyle customCellStyle(Workbook wb, POICustomStyle poiCustomStyle){
        CellStyle cellStyle = wb.createCellStyle();

        if (poiCustomStyle.equals(POICustomStyle.ALIGN_RIGHT)){//判断为居右对齐，执行居右对齐的方法
            cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        }else if (poiCustomStyle.equals(POICustomStyle.ALIGN_LEFT)){//判断为居左对齐，执行居左对齐的方法
            cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        }else if (poiCustomStyle.equals(POICustomStyle.ALIGN_CENTER)){//判断为居中对齐，执行居中对齐的方法
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        }
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 自动换行
        cellStyle.setWrapText(true);

        // 边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);

        return cellStyle;
    }





    /**
     * 读取Excel表格的第一个sheet
     * @param fis
     * @param sheetIndex
     * @return
     *
     * @author xc
     */
    @SuppressWarnings("resource")
	public static HSSFSheet readExcelSheet(InputStream fis, int sheetIndex) {
        HSSFSheet _hssfSheet = null ;
        try {
            POIFSFileSystem _fileSystem = new POIFSFileSystem(fis) ;
            HSSFWorkbook _workbook = new HSSFWorkbook(_fileSystem) ;

            _hssfSheet = _workbook.getSheetAt(sheetIndex) ;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return _hssfSheet ;
    }

    /**
     * 从Excel第一行开始读取数据
     * @return
     *
     *  @author xc
     */
    public static ArrayList<ArrayList<String>> readSheetDataByCell0 (HSSFSheet sheet, int headRowIndex, int startRowIndex, boolean filtEmpty, String dateFormat) {
        ArrayList<ArrayList<String>> result = null;
        if (sheet != null) {
            HSSFRow headRow = sheet.getRow(headRowIndex);
            int bc = 0;//开始列索引
            int ec = headRow.getLastCellNum();//结束列索引
            //System.out.println(bc+"~"+ec);
            int lastRowIndex = sheet.getLastRowNum();//结束行索引
            if (startRowIndex >= 0 && lastRowIndex >= startRowIndex) {
                result = new ArrayList<ArrayList<String>> ();
                for (int i = startRowIndex; i <= lastRowIndex; i++) {
                    ArrayList<String> lt = readRowValue (sheet, i, bc, ec, filtEmpty, dateFormat);
                    if (lt != null && !lt.isEmpty()) {
                        boolean b = false;
                        for (String s : lt) {
                            if (s != null && !"".equals(s.trim())) {
                                b = true;
                                break;
                            }
                        }
                        if (b) {
                            result.add(lt);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 读取一行的内容
     * @param sheet
     * @param rowIndex
     * @param startCellIndex
     * @param endCellIndex
     * @param filtEmpty
     * @param dateFormat
     * @return
     */
    public static ArrayList<String> readRowValue(HSSFSheet sheet, int rowIndex, int startCellIndex, int endCellIndex, boolean filtEmpty, String dateFormat) {
        ArrayList<String> result = null;
        if (sheet != null) {
            HSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                if (startCellIndex >= 0 && endCellIndex > startCellIndex) {
                    result = new ArrayList<String> ();
                    for (int i = startCellIndex; i < endCellIndex; i++) {
                        HSSFCell cell = row.getCell(i);
                        String v = getStringCellValue(cell, dateFormat);
                        if (v != null) {
                            v = v.trim();
                            if (filtEmpty) {
                                if (!"".equals(v)) {
                                    result.add(v);
                                }
                            } else {
                                result.add(v);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     *  获取表格字符串内容
     *  指定日期格式
     * @param cell
     * @param dateFormat
     * @return
     * @author xc
     */
    private static String getStringCellValue (HSSFCell cell, String dateFormat) {
        if (cell != null) {
            String cellValue = "";
            switch (cell.getCellType()) {

                case HSSFCell.CELL_TYPE_STRING://字符串
                    cellValue = cell.getRichStringCellValue().getString();
                    break;

                case HSSFCell.CELL_TYPE_NUMERIC://数字或日期
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        //日期
                        cellValue = convertDateTime(cell.getDateCellValue(), dateFormat);
                    } else {
                        //数字
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellValue = cell.getRichStringCellValue().getString();
                    }
                    break;

                case HSSFCell.CELL_TYPE_BOOLEAN://布尔
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;

                case HSSFCell.CELL_TYPE_FORMULA://公式
                    cellValue = cell.getCellFormula();
                    break;

                case HSSFCell.CELL_TYPE_BLANK://空值
                    cellValue = "";
                    break;

                case HSSFCell.CELL_TYPE_ERROR://错误
                    cellValue = "";
                    break;

                default://默认值
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
            }

            return cellValue;
        } else {
            return "";
        }
    }

    /**
     * 转化时间成指定格式的字符串
     * @param currentDate
     * @param datetimeFormat
     * @return
     * @author xc
     */
    public static synchronized String convertDateTime(Date currentDate, String datetimeFormat) {
        if (currentDate == null || "".equals(currentDate)) {
            return "";
        }
        else if (datetimeFormat == null || "".equals(datetimeFormat)) {
            return "";
        }
        else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(datetimeFormat);
                return formatter.format(currentDate);
            } catch (Exception e) {
                return "";
            }
        }
    }





}
