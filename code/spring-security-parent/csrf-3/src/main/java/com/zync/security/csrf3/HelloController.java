package com.zync.security.csrf3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author luocong
 */
@Controller
public class HelloController {

    @PostMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello")
    public String hello2() {
        return "hello";
    }
}
