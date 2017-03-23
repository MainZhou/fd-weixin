package com.boyoi.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 代码自动生成工具帮助类
 *
 * @author Chenj
 */
public class CreatorUtil {

    private static final Logger log = LoggerFactory.getLogger(CreatorUtil.class);

    /**
     * 生成文件
     * 带备份功能
     * @param content 待写入的内容
     * @param fileName 文件名称
     */
    public static void createFileWithoutCover(StringBuilder content,String fileName) throws IOException{

        File file = new File(fileName);

        //判断文件是否存在,存在备份.
        if(file.exists()){
            //判断是否存在备份文件,有就删除
            File bakFile = new File(fileName+".bak");
            if(bakFile.exists())
                if(bakFile.setExecutable(true))
                    if (!bakFile.delete())
                        throw new RuntimeException("以前的备份文件删除失败!请检查文件:"+fileName);

            //备份文件
            if(!file.renameTo(bakFile))
                throw new RuntimeException("备份文件失败!请检查文件:"+fileName);

        }

        //成功备份,创建新的文件.并把重新写入新内容
        makeFile(content, fileName);
    }

    /**
     * 生成文件
     * 不备份文件
     * @param content 待写入的内容
     * @param fileName 文件名称
     */
    public static void createFileWithCover(StringBuilder content,String fileName) throws IOException{
        makeFile(content, fileName);
    }

    /**
     * 生成文件
     * @param content 内容
     * @param fileName 文件名
     * @throws IOException
     */
    private static void makeFile(StringBuilder content, String fileName) throws IOException {
        File file = new File(fileName);
        BufferedOutputStream bos = null;
        try {
            if (!file.exists()){ //文件不存在,就创建一个
                if (!file.getParentFile().exists())
                    if(!file.getParentFile().mkdir())
                        throw new RuntimeException("文件写入失败,因为不能创建父目录!"+fileName);

                if (!file.createNewFile())
                    throw new RuntimeException("文件写入失败,因为不能创建文件!"+fileName);
            }


            if(file.canWrite()){
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(content.toString().getBytes("UTF-8"));
            }else
                throw new RuntimeException("文件写入失败,因为不能写入此文件!"+fileName);

            log.info("成功写入文件:"+fileName);
        }finally {
            //释放资源
            try{
                if(null != bos)
                    bos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
