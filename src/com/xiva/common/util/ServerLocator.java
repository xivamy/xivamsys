package com.xiva.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ServerLocator implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ServerLocator.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public static Object getService(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
    public static Object getService(Class<?> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }
}
