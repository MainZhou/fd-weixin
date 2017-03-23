package com.boyoi.core.utils;

/**
 * 字符操作的帮助类
 *
 * @author Chenj
 */
public class StringUtil {


    /**
     * 正则表达式,只允许大小写字母,0-9数字
     */
    private static final String prefixReg ="[^a-zA-Z0-9]";

    /**
     * 列名转化待翻译字符(大写字母、-或_ 前加一个空格)
     * @param columnName 列名
     * @return 转化后的字母
     */
    public static String columnName2Translate(String columnName) {
        if (null != columnName) {
            String[] split = columnName.split("[-|_]");
            String result = "";
            if (split.length > 1) {//以_ - 区分
                for (String str : split) {
                    result += str + " ";
                }
            } else {//以大写区分,在大写字母前面加空格
                split = columnName.split("[A-Z]");
                int len = 0;
                if (split.length > 0) {
                    for (String str : split) {
                        if(len==0){//第一次
                            result += str + " ";
                            len += str.length();
                        }
                        else{//以后的每次
                            result += columnName.charAt(len) + str + " ";
                            len += str.length()+1;
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 表名转类名前缀
     * 去除第一个 "-" 或 "_" 前的字符
     * 去除特殊字符,只允许大小写字母,0-9数字
     *
     * @param tableName 表名
     * @return 去除后的字符
     */
    public static String tableName2Prefix(String tableName){
        if(null != tableName){
            //获得第一个 _  - 后的字符
            tableName = get_AfterString(tableName);

            int tableLen = tableName.length();
            String[] split = tableName.split("[_|-]");
            //中间包含 - 或 _  取出来.并设置取出来的字符数组第一个字母大写
            if (split.length>1){
                String tmp = "";
                int strLen;
                for (String str:split){
                    //先把每个字符数组,的特殊字符清除
                    str = str.replaceAll(prefixReg,"");
                    strLen = str.length();
                    if(strLen > 1){//处理二个以上的字母
                        tmp += String.valueOf(str.charAt(0)).toUpperCase()+
                                str.substring(1,strLen).toLowerCase();
                    }else if (strLen >0){//处理只有一个字母
                        tmp += String.valueOf(str.charAt(0)).toUpperCase();
                    }
                }
                return tmp;
            }
            //中间不包含-或_ 去除特殊字符,并把第一个字母大写
            tableName = tableName.replaceAll(prefixReg,"");
            return String.valueOf(tableName.charAt(0)).toUpperCase() +
                    tableName.substring(1,tableLen).toLowerCase() ;
        }
        return null;
    }

    /**
     * 取得第一个"_"或"-"后面的字符
     * @param str 待处理字符
     * @return 处理后字符
     */
    public static String get_AfterString(String str) {
       int strLen = str.length();
       if( strLen > 1){
           int i = str.indexOf("_");
           if( i > 0){
               return str.substring(i+1,strLen);
           }else {
               i = str.indexOf("-");
               if( i > 0){
                   return str.substring(i+1,strLen);
               }
           }
       }
       return null;
    }

}
