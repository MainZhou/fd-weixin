package com.boyoi.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * java Bean转Map 工具类
 * @author Chenj
 */
public class Bean2MapUtil {
    /**
     * bean 转 map
     * @param bean Bean
     * @return map
     */
    @SuppressWarnings("rawtypes")
	public static Map<String, Object> bean2Map(Object bean){
        try {
            Class type = bean.getClass();
            Map<String, Object> returnMap = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : propertyDescriptors) {
                String propertyName = pd.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = pd.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null)
                        returnMap.put(propertyName, result);
                    else
                        returnMap.put(propertyName, "");

                }
            }
            return returnMap;
        }catch (Exception e){
            return null;
        }
    }

}
