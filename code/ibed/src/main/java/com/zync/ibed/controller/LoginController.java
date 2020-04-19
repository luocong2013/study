package com.zync.ibed.controller;

import com.zync.ibed.common.ResultApi;
import com.zync.ibed.common.ResultApiFactory;
import com.zync.ibed.bean.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 登录控制器
 * @date 2019/6/22 18:42
 */
@RestController
public class LoginController {

    /**
     * 登录方法
     * @param user 用户信息
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResultApi> login(@RequestBody UserVO user) {
        if (StringUtils.equals(user.getUsername(), "admin") && StringUtils.equals(user.getPassword(), "123456")) {
            return ResponseEntity.ok(ResultApiFactory.buildSuccessResult("登录成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultApiFactory.buildFailResult("登录失败"));
    }

}
