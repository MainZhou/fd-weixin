package com.boyoi.core.utils;

import com.boyoi.core.enums.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author Chenj
 */
public class FileUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_hh_mm_ss");

    /**
     * 保存文件到磁盘
     * @param file springmvc封装好的文件
     * @param savePath 保存的磁盘路径
     * @param suffix 文件的后缀名
     * @return 保存后的文件目录
     */
    public static String copy2Disk(MultipartFile file, String savePath, String suffix){
        if(null != file){
            String originalFilename = file.getOriginalFilename();
            if(null != suffix){
                try {
                    //保存目录是否存在,不存在,生成目录
                    File pathFile = new File(savePath);
                    if(!pathFile.exists())
                        if(!pathFile.mkdirs())
                            return null;

                    //生成新的文件名
                    String genFilename = genNewFilename(originalFilename, suffix);
                    //保存到目录中
                    file.transferTo(new File(pathFile, genFilename));
                    return genFilename;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }else {
                throw new RuntimeException("文件格式错误!!");
            }
        }
        return null;
    }

    /**
     * 保存文件到磁盘
     * @param file springmvc封装好的文件
     * @param savePath 保存的磁盘路径
     * @return 保存后的文件目录
     */
    public static String copy2Disk(MultipartFile file, String savePath){
        String suffix = getFileSuffix(file.getOriginalFilename());
        return copy2Disk(file, savePath, suffix);
    }

    /**
     * 获得文件的后缀名
     * @param filename 文件名
     * @return 后缀名,如没找到返回null
     */
    public static String getFileSuffix(String filename){
        int fileTypeIndex = filename.lastIndexOf('.');
        //找得到对应的后缀后才返回
        if (fileTypeIndex != -1)
            return filename.substring(fileTypeIndex + 1, filename.length());
        return null;
    }

    /**
     * 重新生成新的文件名
     * 在原有文件名上增加日期
     * @param originalFilename 客户端上传的文件名
     * @param suffix 允许上传的文件后缀名
     * @return 重新生成的文件名
     */
    public static String genNewFilename(String originalFilename, String suffix){
        if(null != originalFilename && !"".equals(originalFilename)){
            return originalFilename.substring(0, originalFilename.lastIndexOf('.')) +
                    sdf.format(new Date()) +
                    "." +
                    suffix;
        }else {//通过uuid生成
            return UUID.randomUUID().toString()+
                    "."+
                    suffix;
        }
    }

    /**
     * 根据文件名判断是否允许上传
     * @param filename 文件名
     * @param fileTypeEnums 允许文件上传类型的枚举
     * @return String 获得的文件后缀名,如不为空就说明能上传
     */
    public static String isAllowFileUpload(String filename, FileTypeEnum... fileTypeEnums){
        if(null != filename && !"".equals(filename)){
            //得到文件后缀名,不包含.
            String suffix = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
            //判断后缀名是否合法
            String allowFileNames[];
            for (FileTypeEnum fileTypeEnum : fileTypeEnums){
                allowFileNames = fileTypeEnum.getTypes();
                for (String allowFileName : allowFileNames){
                    if(suffix.equals(allowFileName)){
                        return allowFileName;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 递归查找文件
     * @param baseDirName  查找的文件夹路径
     * @param findSonDir  是否查找子目录
     * @param targetFileName  需要查找的文件名
     * @return  查找到的文件集合
     */
    public static List<File> findFiles(String baseDirName,Boolean findSonDir, String targetFileName) {

        List<File> findResultList = new ArrayList<>();

        // 创建一个File对象
        File baseDir = new File(baseDirName);
        // 判断目录是否存在
        if (!baseDir.exists() || !baseDir.isDirectory())
            throw new RuntimeException("文件查找失败：" + baseDirName + "不是一个目录！");

        //迭代目录
        String tempName;
        File[] files = baseDir.listFiles();
        if ( null != files){
            for (File file : files) {
                if(findSonDir && file.isDirectory()){//查找子目录
                    findFiles(file.getAbsolutePath(),true, targetFileName);
                }else if(file.isFile()){//不查找目录,并且是文件
                    tempName = file.getName();
                    if(wildcardMatch(targetFileName, tempName)){
                        // 匹配成功，将文件名添加到结果集
                        findResultList.add(file.getAbsoluteFile());
                    }
                }
            }
        }

        return findResultList;
    }

    /**
     * 通配符匹配
     * @param pattern    通配符模式
     * @param str    待匹配的字符串
     * @return    匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                //通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                //通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    //表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

}
