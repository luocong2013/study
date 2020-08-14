package com.zync.security.springsecurity.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 登录控制器
 * @date 2020/7/11 20:49
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login-success", produces = {"text/plain;charset=UTF-8"})
    public String loginSuccess() {
        return "登录成功";
    }

    /**
     * 资源r1访问接口
     * @return
     */
    @GetMapping(value = "/r/r1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String r1() {
        return "访问资源r1";
    }

    /**
     * 资源r2访问接口
     * @return
     */
    @GetMapping(value = "/r/r2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String r2() {
        return "访问资源r2";
    }
}
