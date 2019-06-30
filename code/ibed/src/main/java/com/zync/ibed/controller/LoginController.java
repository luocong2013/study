package com.zync.ibed.controller;

import com.zync.ibed.common.ResultApi;
import com.zync.ibed.common.ResultApiFactory;
import com.zync.ibed.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 登录控制器
 * @date 2019/6/22 18:42
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String toLogin() {
        return "login/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultApi login(@RequestBody UserVO user) {
        if (StringUtils.equals(user.getUsername(), "张三") && StringUtils.equals(user.getPassword(), "123456")) {
            return ResultApiFactory.buildSuccessResult("登录成功");
        }
        return ResultApiFactory.buildFailResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

}
