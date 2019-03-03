package com.ccyw.springboot.dao;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring Bean初始化/销毁的三种方式
 * 1. 在指定方法上加@PostConstruct或@PreDestroy注解
 * 2. 通过实现InitializingBean/DisposableBean接口来定制初始化之后/销毁之前的操作方法
 * 3. 通过为bean指定 initMethod/destroyMethod 属性来指定初始化之后/销毁之前的操作方法
 *
 * 三种初始化顺序
 * Constructor > @PostConstruct > InitializingBean > initMethod
 *
 * 实例化Bean的过程
 * 1. 调用InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()方法，如果该Bean实现了InstantiationAwareBeanPostProcessor接口
 * 2. 创建Bean实例
 * 3. 调用InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()方法，如果该Bean实现了InstantiationAwareBeanPostProcessor接口
 * 4. 设置Bean属性值
 * 5. 调用Bean中的BeanNameAware.setBeanName()方法，如果该Bean实现了BeanNameAware接口
 * 6. 调用Bean中的BeanFactoryAware.setBeanFactory()方法，如果该Bean实现了BeanFactoryAware接口
 * 7. 调用BeanPostProcessor.postProcessBeforeInitialization()方法（@PostConstruct注解后的方法就是在这里被执行的）
 * 8. 调用InitializingBean的afterPropertiesSet()方法
 * 9. 调用Bean中的initMethod，通常是在配置bean的时候指定了initMethod
 * 10. 调用BeanPostProcessor.postProcessAfterInitialization()方法
 * 11. 如果该Bean是单例的，则当容器销毁并且该Bean实现了DisposableBean接口的时候，调用destory方法；如果该Bean是prototype，则将准备好的Bean提交给调用者，后续不再管理该Bean的生命周期
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/11 23:20
 */
public class UserDao implements InitializingBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDao() {
        System.out.println("UserDao 构造函数 ......");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("UserDao PostConstruct ......");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserDao afterPropertiesSet ......");
    }

    public void init() {
        System.out.println("UserDao init ......");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("UserDao PreDestroy ......");
    }

    public void destroy() {
        System.out.println("UserDao destroy ......");
    }

    public void say() {
        System.out.println("UserDao say ......" + getName());
    }

    public String hi() {
        return "hi";
    }

}
