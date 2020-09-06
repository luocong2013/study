package com.zync.single.security.jwt.singlesecurityjwt.web.controller;

import com.zync.single.security.jwt.singlesecurityjwt.web.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 资源控制器
 * @date 2020/8/28 18:25
 */
@Slf4j
@RestController
public class ResourceController {

    /**
     * 需要有 SystemUser 权限的人才能访问
     *
     * @param username
     * @return
     */
    @GetMapping("/users/{username}")
    public UserVO users(@PathVariable String username, Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String s = user.getPrincipal().toString();
        String name = user.getName();
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");
        Claims body = Jwts.parser().setSigningKey("123".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();

        String usern = (String) body.get("user_name");
        log.info("解析token获取到的username为{}", usern);
        log.info("从Authentication里获取到的username为{}", s);
        log.info("从Authentication里获取到的username为{}", name);


        return new UserVO(username, username + "@outlook.com");
    }

    /**
     * 需要有 api 权限的人才能访问
     *
     * @param username
     * @return
     */
    @GetMapping("/api/{username}")
    public String api(@PathVariable String username) {
        return "对接系统：" + username;
    }

    /**
     * 只需认证通过就可访问
     *
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    public UserVO user(@PathVariable String username) {
        return new UserVO(username, username + "@foxmail.com");
    }

}
