package com.zync.body.controller;

import com.zync.body.annotation.DecodeBody;
import com.zync.body.annotation.EncodeBody;
import com.zync.body.model.UserBO;
import com.zync.body.model.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 18:46
 */
@RestController
public class DemoController {

    @PostMapping("/decode/user")
    public ResponseEntity<UserVO> decodeUser(@RequestBody @DecodeBody UserBO user) {
        return ResponseEntity.ok(new UserVO(user.getName(), "成都市武侯区天泰路", 22));
    }

    @EncodeBody
    @GetMapping("/encode/user")
    public ResponseEntity<UserVO> encodeUser() {
        return ResponseEntity.ok(new UserVO("张四", "成都市武侯区天泰路", 20));
    }

}
