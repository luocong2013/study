package com.zync.security.springsecurity.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 登录控制器
 * @date 2020/7/11 20:49
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login-success", produces = {"text/plain;charset=UTF-8"})
    public String loginSuccess() {
        return getUsername() + " 登录成功";
    }

    /**
     * 资源r1访问接口
     * @return
     */
    @GetMapping(value = "/r/r1", produces = MediaType.APPLICATION_JSON_VALUE)
    // 有p1权限才能访问
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return getUsername() + " 访问资源r1";
    }

    /**
     * 资源r2访问接口
     * @return
     */
    @GetMapping(value = "/r/r2", produces = MediaType.APPLICATION_JSON_VALUE)
    // 有p2权限才能访问
    @PreAuthorize("hasAuthority('p2')")
    public String r2() {
        return getUsername() + " 访问资源r2";
    }

    /**
     * 获取当前用户信息
     * @return
     */
    private String getUsername() {
        // 当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 用户身份信息
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            return "匿名";
        }
        String username;
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
