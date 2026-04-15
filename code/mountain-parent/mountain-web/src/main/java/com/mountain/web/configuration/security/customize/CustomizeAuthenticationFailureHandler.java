package com.mountain.web.configuration.security.customize;

import com.mountain.common.base.ApiResponseEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p> 自定义 AuthenticationFailureHandler </p>
 * <p> 认证失败处理，前后端分离情况下返回json数据格式 </p>
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 15:30
 **/
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ApiResponseEntity.error(HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception.getMessage(),
                exception.toString()).out(response);
    }
}
