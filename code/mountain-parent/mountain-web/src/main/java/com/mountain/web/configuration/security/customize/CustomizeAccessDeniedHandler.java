package com.mountain.web.configuration.security.customize;

import com.mountain.common.base.ApiResponseEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * <p> 自定义 AccessDeniedHandler </p>
 * <p> 认证url权限 - 登录后访问接口无权限 - 自定义403无权限响应内容 </p>
 * <p> 注意：这是登录过后的权限处理，要和未登录时的权限处理区分开 </p>
 *
 * @see org.springframework.security.web.access.ExceptionTranslationFilter
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/14 21:59
 */
@Slf4j
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException deniedException) throws IOException, ServletException {
        log.error("Access denied error", deniedException);
        ApiResponseEntity.error(HttpStatus.FORBIDDEN,
                deniedException.getMessage(),
                deniedException.getMessage(),
                deniedException.toString()).out(response);
    }
}
