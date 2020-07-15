package com.zync.security.session.service;

import com.zync.security.session.model.AuthenticationRequest;
import com.zync.security.session.model.UserDTO;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户认证接口
 * @date 2020/7/11 14:44
 */
public interface AuthenticationService {

    /**
     * 用户认证
     * @param authenticationRequest 用户认证请求，账号和密码
     * @return 认证成功的用户信息
     */
    UserDTO authentication(AuthenticationRequest authenticationRequest);
}
