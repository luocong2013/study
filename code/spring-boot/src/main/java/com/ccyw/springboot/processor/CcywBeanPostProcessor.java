package com.ccyw.springboot.processor;

import com.ccyw.springboot.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @description Bean后置处理器
 * @date 2018/11/11 21:41
 */
@Component
public class CcywBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化之前进行增强处理 ......");
        if (StringUtils.equals(beanName, "userDao") || bean instanceof UserDao) {
            UserDao userDao = (UserDao) bean;
            userDao.setName("张三");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化之后进行增强处理 ......");
        return bean;
    }
}
