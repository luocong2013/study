package com.mountain.web.configuration.security.customize;

import com.mountain.common.base.ApiResponseEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * <p> 自定义 AuthenticationEntryPoint </p>
 * <p> 未登录的情况下访问所有接口都会拦截到此 </p>
 *
 * @see org.springframework.security.web.access.ExceptionTranslationFilter
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/14 22:06
 **/
@Slf4j
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String MESSAGE = "未登陆或登录已过期";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication error, not login or token expired", authException);
        ApiResponseEntity.error(HttpStatus.UNAUTHORIZED, MESSAGE,
                authException.getMessage(),
                authException.toString()).out(response);
    }
}
