package com.zync.security.session.service.impl;

import com.zync.security.session.model.AuthenticationRequest;
import com.zync.security.session.model.UserDTO;
import com.zync.security.session.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户认证接口实现
 * @date 2020/7/11 14:50
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public UserDTO authentication(AuthenticationRequest authenticationRequest) {
        if (Objects.isNull(authenticationRequest) ||
                StringUtils.isBlank(authenticationRequest.getUsername()) ||
                StringUtils.isBlank(authenticationRequest.getPassword())) {
            throw new RuntimeException("账号或密码为空");
        }

        UserDTO user = getUserDTO(authenticationRequest.getUsername());
        if (Objects.isNull(user)) {
            throw new RuntimeException("查询不到该用户");
        }
        // 校验密码
        if (!StringUtils.equals(authenticationRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        // 认证通过
        return user;
    }

    /**
     * 根据账号查询用户信息
     * @param username 用户名
     * @return
     */
    private UserDTO getUserDTO(String username) {
        return userMap.get(username);
    }


    /**
     * 模拟数据
     */
    private Map<String, UserDTO> userMap = new HashMap<>(16);
    {
        Set<String> authorities1 = new HashSet<>();
        // 这个p1权限，我们让他和资源/r/r1对应
        authorities1.add("p1");
        Set<String> authorities2 = new HashSet<>();
        // 这个p2权限，我们让他和资源/r/r2对应
        authorities2.add("p2");
        userMap.put("zhangsan", UserDTO.builder().id("1010").username("zhangsan")
                .password("123").fullname("张三").mobile("1334553").authorities(authorities1).build());
        userMap.put("lisi", UserDTO.builder().id("1011").username("lisi")
                .password("456").fullname("李四").mobile("1445551").authorities(authorities2).build());
    }
}
