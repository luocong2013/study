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

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
