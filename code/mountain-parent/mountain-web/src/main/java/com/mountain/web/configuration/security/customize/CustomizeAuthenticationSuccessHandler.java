package com.mountain.web.configuration.security.customize;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.mountain.common.base.ApiResponseEntity;
import com.mountain.web.configuration.security.details.SecurityUser;
import com.mountain.web.configuration.security.details.UserLoginVO;
import com.mountain.web.configuration.security.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p> 自定义 AuthenticationSuccessHandler </p>
 * <p> 认证成功处理 </p>
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 15:37
 **/
@Component
@RequiredArgsConstructor
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        UserLoginVO user = new UserLoginVO();
        user.setUsername(securityUser.getUsername());
        user.setAccessToken(tokenService.createToken(securityUser));
        user.setRefreshToken(IdWorker.getIdStr());
        ApiResponseEntity.success(user).out(response);
    }
}
