package com.mountain.web.configuration.security.customize;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * <p> 自定义 AuthenticationDetailsSource </p>
 * <p> 登录过程中对用户的登录信息的详细信息进行填充 </p>
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/10 14:26
 **/
public class CustomizeAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CustomizeWebAuthenticationDetails> {

    @Override
    public CustomizeWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CustomizeWebAuthenticationDetails(context);
    }

}
