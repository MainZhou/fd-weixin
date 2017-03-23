package com.boyoi.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * 读取excel公式
 * @author Chenj
 */
public class ReadExcel {
    @Test
    public void test() throws Exception{
        File file = new File("c:\\中小型终端供气方供气月度核算表.xls");

        @SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));

        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row = sheet.getRow(10);

        HSSFCell cell = row.getCell(2);
        String cellFormula = cell.getCellFormula();
        double numericCellValue = cell.getNumericCellValue();
        System.out.println("公式：" + cellFormula);
        System.out.println("值  ：" +numericCellValue);

    }
}
