package com.boyoi.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * @author Chenj
 */
public class Dom4jTest {
    @SuppressWarnings({ "rawtypes", "unused" })
	@Test
    public void parse() throws Exception{
        SAXReader saxReader = new SAXReader();
        Document pdm = saxReader.read(new File("c:\\PMS_141124.pdm"));
        Iterator tabIterator = pdm.selectNodes("//c:Tables//o:Table").iterator();
        while (tabIterator.hasNext()){//迭代所有的表
            Element tables = (Element) tabIterator.next();
            String name = tables.elementTextTrim("Name");//表名中文名字
            String code = tables.elementTextTrim("Code");//tableName
            Iterator colIterator = tables.element("Columns").elements("Column").iterator();
            while (colIterator.hasNext()){//迭代表中所有的列名
                Element columns = (Element) colIterator.next();
                String colName = columns.elementTextTrim("Name");
                String colCode = columns.elementTextTrim("Code");
                System.out.println(colCode + "  的中文名为-->" +colName);
            }
        }
    }
    
  	@Test
  	public void TestOne() throws Exception{
    	
        System.out.println("数字："+new DecimalFormat("0.00").format(13256985.02365)) ;
    }
}
