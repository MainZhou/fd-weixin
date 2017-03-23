package com.boyoi.work.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 *
 * @author zhoujl
 * @Date 2016/5/20 9:43
 */
public class FileUtils {
    /**
     * 上传
     *
     * @param request 请求
     * @param file    文件
     * @param modular 模块名
     * @return 上传后的路径
     */
    public static String upload(HttpServletRequest request, MultipartFile file, String modular) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                String type = file.getOriginalFilename().substring(
                        file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                String fileNameHead = file.getOriginalFilename().substring(0,
                        file.getOriginalFilename().indexOf("."));
                String filename = System.currentTimeMillis() + fileNameHead + type;// 取当前时间戳和模块名作为文件名
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/") + "attached/" + modular + "/" + filename;
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return "attached/" + modular + "/" + filename;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 上传
     *
     * @param request 请求
     * @param file    文件
     * @param modular 模块名
     *                zy
     */
    public static String uploadFile(HttpServletRequest request, MultipartFile file, String modular) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                String type = file.getOriginalFilename().substring(
                        file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                String fileNameHead = file.getOriginalFilename().substring(0,
                        file.getOriginalFilename().indexOf("."));
                //转换日期输出格式
                Date dateLogs = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String oTime = dateFormat.format(dateLogs);
                String filename = oTime + fileNameHead + type;// 取当前时间戳和模块名作为文件名
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/") + "attached/" + modular + "/" + filename;
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return "attached/" + modular + "/" + filename;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 保存文件内存(默认保存到classpath下的文件)
     * @param filePath 文件路径
     * @param content 文件内容
     */
    public static void saveFile(String filePath, Map<String,String> content){
        BufferedReader br = null;
        BufferedWriter bw = null;
        String line;
        StringBuffer buf = new StringBuffer();

        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(filePath));

            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                // 此处根据实际需要修改某些行的内容
                boolean boo = true;
                for (String key : content.keySet()) {
                    if (line.contains(key)) {
                        buf.append(key).append(content.get(key));
                        boo=false;
                    }
                }
                //没有修改的按照原来的存入
                if(boo){
                    buf.append(line);
                }
                buf.append(System.getProperty("line.separator"));//换行符
                bw = new BufferedWriter(new FileWriter(filePath));
                bw.write(buf.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }
}
