package com.zync.security.session.controller;

import com.zync.security.session.model.AuthenticationRequest;
import com.zync.security.session.model.UserDTO;
import com.zync.security.session.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 登录控制器
 * @date 2020/7/11 15:01
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 登录接口
     * @param authenticationRequest
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(AuthenticationRequest authenticationRequest, HttpSession httpSession) {
        UserDTO user = authenticationService.authentication(authenticationRequest);
        // 存入session
        httpSession.setAttribute(UserDTO.SESSION_USER_KEY, user);
        return user.getFullname() + " 登录成功";
    }

    /**
     * 退出接口
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public String logout(HttpSession httpSession) {
        // 清除session
        httpSession.invalidate();
        return "退出成功";
    }

    /**
     * 资源r1访问接口
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/r/r1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String r1(HttpSession httpSession) {
        String fullname;
        Object object = httpSession.getAttribute(UserDTO.SESSION_USER_KEY);
        if (Objects.isNull(object)) {
            fullname = "匿名";
        } else {
            UserDTO user = (UserDTO) object;
            fullname = user.getFullname();
        }
        return fullname + " 访问资源r1";
    }

    /**
     * 资源r2访问接口
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/r/r2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String r2(HttpSession httpSession) {
        String fullname;
        Object object = httpSession.getAttribute(UserDTO.SESSION_USER_KEY);
        if (Objects.isNull(object)) {
            fullname = "匿名";
        } else {
            UserDTO user = (UserDTO) object;
            fullname = user.getFullname();
        }
        return fullname + " 访问资源r2";
    }
}
