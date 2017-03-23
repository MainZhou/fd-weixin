package com.boyoi.core.utils;

import java.security.MessageDigest;

/**
 * 加密工具(加密为MD5,SHA1...)
 *
 * @author Chenj
 */
public class EncryptUtil {

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 当前系统默认加密方式
     * @param str 待加密的字符
     * @return 加密后的字符
     */
    public static String current(String str){
        return SHA1(SHA1(str));
    }

    /**
     * 通过自定义加密算法实现加密
     *
     * @param algorithm 加密算法名称
     * @param str 待加密字符串
     * @return String
     */
    public static String diy(String algorithm, String str) {
        if(null != str){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new NullPointerException("被加密字符不能为空!");
        }
    }

    /**
     * MD5加密
     *
     * @param str 被加密字符串
     * @return 加密后字符串
     */
    public static String MD5(String str) {
        if(null != str){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new NullPointerException("被加密字符不能为空!");
        }
    }

    /**
     * SHA1加密
     *
     * @param str 被加密字符串
     * @return 加密后字符串
     */
    public static String SHA1(String str) {
        if( null != str){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new NullPointerException("被加密字符不能为空!");
        }
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式(经过特殊位移了)
        for (byte b : bytes) {
            buf.append(HEX_DIGITS[(b >> 3) & 0x0f]);
            buf.append(HEX_DIGITS[(b >> 1) & 0x0f]);
        }
        return buf.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        System.out.println("111111 SHA1 :"
//                + EncryptUtil.MD5("111111"));
//        System.out.println(System.currentTimeMillis());
//
//    }


}
