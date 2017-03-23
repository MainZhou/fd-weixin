package com.boyoi.core.utils;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.*;

/**
 * 序列化与反系列化
 *
 * @author Chenj
 */
public class SerializeUtil {
    /**
     * 序列化
     * @param object 对象
     * @return 字节数组
     */
    public static byte[] serialize(Object object) {
        if(null != object){
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FSTObjectOutput oos = new FSTObjectOutput(baos);
                oos.writeObject(object);
                oos.close();
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            throw new NullPointerException("被序列化对象不能为空!");
        }
    }

    /**
     * 反序列化
     * @param bytes 字节数组
     * @return 对象
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T unserialize(byte[] bytes) {
        if(null != bytes){
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                FSTObjectInput fis = new FSTObjectInput(byteArrayInputStream);
                T t = (T) fis.readObject();
                fis.close();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            throw new NullPointerException("反序列化对象不能为空!");
        }
    }

    /**
     * 把文件反序列化
     * @param file 文件
     * @return 对象
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T unserializeFile(File file) {
        if(null != file && file.exists() && file.isFile()){
            try {
                FSTObjectInput foi = new FSTObjectInput(new FileInputStream(file));
                T t = (T) foi.readObject();
                foi.close();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            throw new NullPointerException("反序列化对象不能为空!");
        }
    }

    /**
     * 把文件反序列化为字节
     * @param file 文件
     * @return 字节数组
     */
    @SuppressWarnings("resource")
	public static byte[] unserializeFile2Bytes(File file) {
        if(null != file && file.exists() && file.isFile()){
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                byte[] buf = new byte[1024];
                int len;
                int pos =0;
                // 重新读的标记
                bis.mark(0);
                // 得到总共多少行
                while ((len = bis.read(buf)) >=0){
                    pos += len;
                }
                // 创建适合文件所有的字节
                byte[] result = new byte[pos];
                bis.reset();
                int read = bis.read(result, 0, pos);
                if (read != pos){
                    throw new NullPointerException("反序列化失败!");
                }else {
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            throw new NullPointerException("反序列化对象不能为空!");
        }
    }
}
