package com.zync.single.security.jdbc.singlesecurityjdbc.web.controller;

import com.zync.single.security.jdbc.singlesecurityjdbc.web.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 资源控制器
 * @date 2020/8/28 18:25
 */
@RestController
public class ResourceController {

    @GetMapping("/user/{username}")
    public UserVO user(@PathVariable String username) {
        return new UserVO(username, username + "@foxmail.com");
    }

}
