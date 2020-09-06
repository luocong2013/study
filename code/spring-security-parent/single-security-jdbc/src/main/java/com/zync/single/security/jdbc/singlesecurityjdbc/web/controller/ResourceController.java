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

    /**
     * 需要有 SystemUser 权限的人才能访问
     * @param username
     * @return
     */
    @GetMapping("/users/{username}")
    public UserVO users(@PathVariable String username) {
        return new UserVO(username, username + "@outlook.com");
    }

    /**
     * 需要有 api 权限的人才能访问
     * @param username
     * @return
     */
    @GetMapping("/api/{username}")
    public String api(@PathVariable String username) {
        return "对接系统：" + username;
    }

    /**
     * 只需认证通过就可访问
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    public UserVO user(@PathVariable String username) {
        return new UserVO(username, username + "@foxmail.com");
    }

}
