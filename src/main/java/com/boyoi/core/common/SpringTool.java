package com.boyoi.core.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 普通类调用Spring bean对象
 * Spring 工具类
 * @author Chenj
 */
public class SpringTool implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (SpringTool.applicationContext == null) {
            SpringTool.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获得spring 管理的 bean
     * @param name bean的名称
     * @return bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获得spring 管理的 bean
     * @param aClass 通过字节获得bean
     * @return bean
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(Class aClass) {
        return getApplicationContext().getBean(aClass);
    }

}
