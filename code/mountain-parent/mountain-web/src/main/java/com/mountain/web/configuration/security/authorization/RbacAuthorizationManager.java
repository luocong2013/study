package com.mountain.web.configuration.security.authorization;

import com.mountain.web.basic.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * RBAC鉴权
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 13:47
 **/
@Component
@RequiredArgsConstructor
public class RbacAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final PermissionService permissionService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // 1. 获取当前登录用户的认证信息
        Authentication auth = authentication.get();
        // 未登录用户直接拒绝访问
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }

        // 2. 获取当前请求的资源信息
        HttpServletRequest request = context.getRequest();
        String url = request.getPathInfo();
        String requestMethod = request.getMethod();

        // 3. 获取当前资源所需的权限标识
        List<String> requiredAuthorities = permissionService.listAuthorities(url, requestMethod);
        if (CollectionUtils.isEmpty(requiredAuthorities)) {
            // 没有拿到权限标识，说明接口是登录即可访问
            return new AuthorizationDecision(true);
        }

        // 4. 获取当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        // 校验：用户所具有的权限包含资源所需的权限则放行
        for (GrantedAuthority authority : authorities) {
            String hasAuthority = authority.getAuthority();
            if (requiredAuthorities.contains(hasAuthority)) {
                return new AuthorizationDecision(true);
            }
        }

        // 5. 否则，拒绝访问
        return new AuthorizationDecision(false);
    }

}
