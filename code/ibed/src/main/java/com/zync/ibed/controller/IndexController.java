package com.zync.ibed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 入口控制器
 * @date 2019/6/16 21:18
 */
@Controller
public class IndexController {

    /**
     * 入口方法
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登录页面跳转方法
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login/login";
    }

    /**
     * 首页跳转方法
     * @return
     */
    @GetMapping("/toHome")
    public String toHome() {
        return "home/home";
    }

}
