package com.boyoi.core.creator;

import com.alibaba.fastjson.JSON;
import com.boyoi.core.domain.ColumnRequest;
import com.boyoi.core.utils.CreatorUtil;
import com.boyoi.core.utils.HttpUtil;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * Creator接口的帮助类.
 * 实现公共方法
 * @author Chenj
 */
public abstract class CreatorHelper implements Creator {

    //项目地址如:http://192.168.1.76/xeg
    protected String basePath;
    //maven项目的java目录
    protected String javaPath;
    //maven项目的resource目录
    protected String resourcePath;
    //maven项目的webapp目录
    protected String webappPath;

    protected ColumnRequest columnRequest;
    protected ServletRequest servletRequest;


    /**
     * 构造函数.初始化
     * @param columnRequest 页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public CreatorHelper(ColumnRequest columnRequest, ServletRequest servletRequest) {
        this.columnRequest = columnRequest;
        this.servletRequest = servletRequest;

        basePath = HttpUtil.getBasePath(servletRequest);

        String mainPath = columnRequest.getAbsPath() +
                File.separator + "src" + File.separator + "main";

        javaPath = mainPath + File.separator + "java";

        resourcePath = mainPath + File.separator + "resources";

        webappPath = mainPath + File.separator + "webapp";

    }

    /**
     * 覆盖接口方法.实现通用的功能
     * @return 成功与否
     */
    @Override
    public String creatorFile() {
        try {
            /**
             * 获得模板的URL
             */
            String tplUrl = this.setTplUrl();

            //获得生成后的文件
            StringBuilder sb;
            if (null != tplUrl)
                sb = HttpUtil.postWithJson(tplUrl, JSON.toJSONString(columnRequest));
            else
                throw new NullPointerException("获得模板的URL不能为空!请设置tplUrl");

            /**
             * 设置生成后的绝对路径
             */
            String genFileName = this.setGenFileName();

            //写入到文件中
            if (null != genFileName){
                if (null != columnRequest.getCover() && columnRequest.getCover())//覆盖生成
                    CreatorUtil.createFileWithCover(sb, genFileName);
                else//非覆盖生成
                    CreatorUtil.createFileWithoutCover(sb, genFileName);
            }
            else
                throw new NullPointerException("生成后的文件不能为空!请设置genFilePath");

            return genFileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 让子类实现此方法.设置文件名称(绝对路径)
     * @return 文件名称(绝对路径)
     */
    protected abstract String setGenFileName();

    /**
     * 让子类实现此方法.获得模板的URL
     * @return 模板的URL
     */
    protected abstract String setTplUrl();
}
