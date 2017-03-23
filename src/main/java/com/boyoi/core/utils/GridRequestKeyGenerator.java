package com.boyoi.core.utils;

import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 针对GridRequest生成key
 * 此处不用针对每个service impl 生成不同的key.
 * @author Chenj
 */
public class GridRequestKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params[0] instanceof EasyGridRequest){
            EasyGridRequest gridRequest = (EasyGridRequest) params[0];
            return target.getClass().getName()+ "_" + gridRequest.getPage() + "_" +
                   gridRequest.getRows() + "_" +
                   gridRequest.getSort() + "_" +
                   gridRequest.getOrder();
        }
        return null;
    }
}
