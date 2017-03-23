package com.boyoi.core.utils;

import com.boyoi.core.common.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.UUID;


/**
 * Web工具类
 */
public class WebUtils {

	// 产生全球唯一的ID
	public static String generateID() {
		return UUID.randomUUID().toString();
	}

    /**
     * 处理包含有验证错误
     * @param result 验证后信息
     * @param responseResult 返回的结果
     */
    public static void execErrors(BindingResult result, ResponseResult responseResult){
        List<ObjectError> allErrors = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError: allErrors){
            sb.append(objectError.getDefaultMessage());
        }
        responseResult.setStatus(false);
        responseResult.setMsg(sb.toString());
    }
}
