package com.boyoi.core.utils;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * fast-serialization 帮助类
 * @author Chenj
 */
public class FstUtil {
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
}
