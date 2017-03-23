package com.boyoi.core.utils;

import com.boyoi.base.domain.ColumnCustom;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 自定义列key生成器
 * @author Chenj
 */
public class CustomColumnKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params[0] instanceof ColumnCustom){
            ColumnCustom columnCustom = (ColumnCustom)params[0];
            return columnCustom.getUserId() + columnCustom.getDomainUrl();
        }else {
            return String.valueOf(params[0]) + String.valueOf(params[1]);
        }
    }
}
