package com.zync.example.springbootdemo.service;

import com.zync.example.springbootdemo.event.UserRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 邮件服务
 * @date 2020/11/18 14:50
 */
@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.println(String.format("用户[%s]注册，邮件服务，发送邮件...", event.getUsername()));
    }
}
