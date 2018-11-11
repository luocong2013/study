package com.ccyw.springboot.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @description 容器后处理器
 * @date 2018/11/11 21:56
 */
@Component
public class CcywBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("对容器进行后处理 ......");
    }
}
