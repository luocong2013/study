package com.zync.security.formlogin2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocong
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/index1")
    public String index1() {
        return "index1";
    }

    @RequestMapping("/index2")
    public String index2() {
        return "index2";
    }

    @RequestMapping("/f1")
    public String f1() {
        return "重定向页面";
    }

    @RequestMapping("/f2")
    public String f2() {
        return "服务端跳转页面";
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "注销成功";
    }
}
