package com.mountain.web.configuration.security.filter;

import com.mountain.common.utils.JacksonUtil;
import com.mountain.common.utils.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 用户登录密码校验过滤器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/10 13:48
 **/
@Slf4j
public class UsernamePasswordLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType() == null || !Strings.CS.contains(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException(StrUtil.format("不支持的请求头类型: {}", request.getContentType()));
        }

        try {
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(null, null);
            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);
            log.info("login details : {}", JacksonUtil.toJson(authRequest.getDetails()));
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.error("attemptAuthentication error", e);
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
