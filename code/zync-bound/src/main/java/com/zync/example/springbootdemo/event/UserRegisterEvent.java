package com.zync.example.springbootdemo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 用户注册事件
 * @date 2020/11/18 14:37
 */
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String username;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
