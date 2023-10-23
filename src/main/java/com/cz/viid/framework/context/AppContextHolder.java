package com.cz.viid.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class AppContextHolder implements ApplicationContextAware {
    private volatile static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }
}
