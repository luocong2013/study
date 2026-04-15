package com.mountain.web.configuration.security.configurers;

import com.mountain.web.configuration.security.customize.CustomizeAuthenticationDetailsSource;
import com.mountain.web.configuration.security.filter.UsernamePasswordLoginAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 用户登录密码校验过滤器Configurer
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 16:50
 **/
public class UsernamePasswordLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractAuthenticationFilterConfigurer<H, UsernamePasswordLoginConfigurer<H>, UsernamePasswordLoginAuthenticationFilter> {

    public UsernamePasswordLoginConfigurer() {
        super(new UsernamePasswordLoginAuthenticationFilter(), "/api/rest/login");
        authenticationDetailsSource(new CustomizeAuthenticationDetailsSource());
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, loginProcessingUrl);
    }

}
