package com.boyoi.core.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * PD文件读取的帮助类
 *
 * @author Chenj
 */
public class PdUtil {

    public static PropertiesUtil propUtil;
    public static File pdFileInCreatorProperties;

    static {
        propUtil = new PropertiesUtil("/creator.properties");
        pdFileInCreatorProperties = new File(propUtil.readString("pdFilePath"));
    }

    /**
     * 从classpath下获得pd文件
     * @return File对象
     */
    public static File getPdFileInClassPath() {
        //查找classpath下面的pdm文件.并读取name,设置到columns的中文名称
        URL resource = PdUtil.class.getResource("/");
        if (null != resource) {
            String basePath = resource.getFile();//classes根目录
            //查找classpath 下面的pdm文件
            List<File> pdms = FileUtil.findFiles(basePath, false, "*.pdm");
            if (pdms.size() >0) {
                //只返回读取到的第一个
               return pdms.get(0);
            }
        }
        return null;
    }

    /**
     * 获得pd文件的路径
     * @return 路径
     */
    public static String getPdFilePath() {
        File file = getPdFileInClassPath();
        if (null != file) {
            return file.getPath();
        }
        return null;
    }

    /**
     * 读取pd 中的对应的tableName下的所有列名
     * @param file pd File
     * @param tableName 表名
     * @return //Map<String,String>  第一个string为列名.第二个string 为列的中文名
     */
    @SuppressWarnings("rawtypes")
	public static Map<String,String> readColumnsByTableName(File file,String tableName){
        //用于存放字段名对应的中文名
        Map<String,String> enAndZh = new HashMap<>();

        try{
            SAXReader saxReader = new SAXReader();
            Document pdm = saxReader.read(file);
            Iterator tabIterator = pdm.selectNodes("//c:Tables//o:Table").iterator();
            while (tabIterator.hasNext()){//迭代所有的表
                Element tables = (Element) tabIterator.next();
                String code = tables.elementTextTrim("Code");//tableName
                //如表名等于用户想查找的.迭代pd下面的Columns
                if( code.equalsIgnoreCase(tableName)){
                    Iterator colIterator = tables.element("Columns").elements("Column").iterator();
                    //迭代表中所有的列名
                    while (colIterator.hasNext()){
                        Element columns = (Element) colIterator.next();
                        String colName = columns.elementTextTrim("Name");
                        String colCode = columns.elementTextTrim("Code");
                        enAndZh.put(colCode,colName);
                    }
                    break;//退出查找table循环
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return enAndZh;
    }
}
