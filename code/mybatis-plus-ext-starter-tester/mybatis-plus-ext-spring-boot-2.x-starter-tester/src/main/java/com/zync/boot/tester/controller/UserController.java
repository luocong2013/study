package com.zync.boot.tester.controller;


import com.zync.boot.tester.entity.User;
import com.zync.boot.tester.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author luocong
 * @since 2022-01-30
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add/v1")
    public ResponseEntity<String> add(@RequestBody List<User> users) {
        boolean row = userService.saveBatch(users, 5);
        return ResponseEntity.ok(String.format("保存数据【%s】.", row ? "成功" : "失败"));
    }

}
