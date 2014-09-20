package com.replash.spring;

import com.replash.Command;
import com.replash.ReplashBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class ReplashCommandDiscoveryBeanPostProcessor implements BeanPostProcessor {
    private final ReplashBuilder replashBuilder;

    public ReplashCommandDiscoveryBeanPostProcessor(ReplashBuilder replashBuilder) {
        this.replashBuilder = replashBuilder;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for(Method method : beanClass.getMethods()) {
            Command commandAnnotation = method.getAnnotation(Command.class);
            if(commandAnnotation != null) {
                replashBuilder.withCommands(bean);
                break;
            }
        }
        return bean;
    }
}
