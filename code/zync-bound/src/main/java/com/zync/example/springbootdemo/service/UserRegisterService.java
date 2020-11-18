package com.zync.example.springbootdemo.service;

import com.zync.example.springbootdemo.event.UserRegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 用户注册服务
 * @date 2020/11/18 14:41
 */
@Service
public class UserRegisterService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String register(String username) {
        // 用户注册....

        // 发布事件...
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));

        return "恭喜你！注册成功";
    }
}
