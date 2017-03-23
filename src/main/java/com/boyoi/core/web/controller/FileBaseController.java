package com.boyoi.core.web.controller;

import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.enums.FileTypeEnum;
import com.boyoi.core.service.BaseCrudService;
import com.boyoi.core.utils.FileUtil;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持文件上传的基类.
 * 此类继承了BaseController,同时也实现了基本的增删改查,语言设置
 * 如需实现文件上传功能,继承此类
 * @author Chenj
 */
public abstract class FileBaseController<T extends BaseDomain,S extends BaseCrudService> extends BaseCrudController<T,S> {

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(@Validated T t, BindingResult result,@RequestParam(value = "file",required = false)MultipartFile[] files, Model model, String... other) {

        //判断是否有错,有错返回添加页面
        if(result.hasErrors()){
            execErrors(result);
            return responseResult;
        }

        //用户上传了文件
        List<String> genFilenames = new ArrayList<>();//生成的文件名称数组
        if(null != files && files.length>0){
            for (MultipartFile file : files){
                if(file.getSize()>0){//文件大小不为空
                    //上传到一级目录下
                    String genFilename = null;
                    try {
                        //判断文件是否被允许上传
                        FileTypeEnum[] allowFileType = setFileType();
                        String fileType = FileUtil.isAllowFileUpload(file.getOriginalFilename(), allowFileType);
                        if (null != fileType)
                            genFilename = FileUtil.copy2Disk(file,
                                    request.getServletContext().getRealPath("/attached") + File.separator + url,
                                    fileType);
                        //文件名为空,提示上传
                        else{
                            addUiInit(t, model);
                            responseResult.setStatus(false);
                            StringBuilder stringBuilder = new StringBuilder();
                            for (FileTypeEnum fileTypeEnum : allowFileType){
                                for (String fileTypeInEnum : fileTypeEnum.getTypes()){
                                    stringBuilder.append(fileTypeInEnum);
                                    stringBuilder.append(" ");
                                }
                            }
                            responseResult.setMsg("请上传:" + stringBuilder.toString());
                            return responseResult;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(null == genFilename){
                        addUiInit(t, model);
                        responseResult.setStatus(false);
                        responseResult.setMsg("上传失败,请稍后在试!");
                        return responseResult;
                    }
                    //添加到上传后的文件名集合
                    genFilenames.add("/attached/"+url+"/"+genFilename);
                }else {//占位
                    genFilenames.add(null);
                }
            }
        }

        //调用子类必须实现的方法,保存在数据库中的文件路径
        if(genFilenames.size()>0)
            setDomainPath(t,genFilenames);

        addFunBefore(t, other);
        service.add(t);
        addFunAfter(t, other);

        responseResult.setStatus(true);
        responseResult.setMsg("上传成功!");

        return responseResult;
    }


    @RequestMapping(value = "upload/update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult uploadUpdate(@Validated T t, BindingResult result,@RequestParam(value = "file",required = false)MultipartFile[] files, Model model, String... other) {

        uploadUpdateBefore(t,model,result);
        //判断是否有错,有错返回修改
        if(result.hasErrors()){
            execErrors(result);
            return responseResult;
        }

        //用户上传了文件
        List<String> genFilenames = new ArrayList<>();//生成的文件名称数组
        if(null != files && files.length>0){
            for (MultipartFile file : files){
                if(file.getSize()>0){//文件大小不为空
                    //上传到一级目录下
                    String genFilename = null;
                    try {
                        //判断文件是否被允许上传
                        FileTypeEnum[] allowFileType = setFileType();
                        String fileType = FileUtil.isAllowFileUpload(file.getOriginalFilename(), allowFileType);
                        if (null != fileType)
                            genFilename = FileUtil.copy2Disk(file,
                                    request.getServletContext().getRealPath("/attached") + File.separator + url,
                                    fileType);
                            //文件名为空,提示上传
                        else{
                            addUiInit(t, model);
                            responseResult.setStatus(false);
                            StringBuilder stringBuilder = new StringBuilder();
                            for (FileTypeEnum fileTypeEnum : allowFileType){
                                for (String fileTypeInEnum : fileTypeEnum.getTypes()){
                                    stringBuilder.append(fileTypeInEnum);
                                    stringBuilder.append(" ");
                                }
                            }
                            responseResult.setMsg("请上传:" + stringBuilder.toString());
                            return responseResult;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(null == genFilename){
                        addUiInit(t, model);
                        responseResult.setStatus(false);
                        responseResult.setMsg("上传失败,请稍后在试!");
                        return responseResult;
                    }
                    //添加到上传后的文件名集合
                    genFilenames.add("/attached/"+url+"/"+genFilename);
                }else {//占位
                    genFilenames.add(null);
                }
            }
        }

        //调用子类必须实现的方法,保存在数据库中的文件路径
        if(genFilenames.size()>0)
            setDomainPath(t,genFilenames);

        service.update(t);

        responseResult.setStatus(true);
        responseResult.setMsg("上传成功!");

        return responseResult;
    }

    /**
     * 更新前需要操作的
     * @param t 实体
     * @param model model
     * @param result 错误结果集
     */
    protected void uploadUpdateBefore(T t, Model model, BindingResult result) {
    }


    /**
     * 让子类必须自己实现此方法
     * 保存生成过后的文件名称到实体中
     * @param t 实体
     * @param genFilenames 生成后的文件名称集合
     */
    protected abstract void setDomainPath(T t,List<String> genFilenames);

    /**
     * 设置允许上传的文件类型
     * @return FileTypeEnum
     */
    protected abstract FileTypeEnum[] setFileType();

}
