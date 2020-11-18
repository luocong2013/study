package com.zync.example.springbootdemo.controller;

import com.zync.example.springbootdemo.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 用户注册控制器
 * @date 2020/11/18 14:40
 */
@RestController
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @GetMapping("/register/{username}")
    public String register(@PathVariable String username) {
        return userRegisterService.register(username);
    }
}
