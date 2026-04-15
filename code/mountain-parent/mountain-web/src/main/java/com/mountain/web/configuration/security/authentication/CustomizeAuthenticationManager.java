package com.mountain.web.configuration.security.authentication;

import com.mountain.common.exception.BizException;
import com.mountain.common.utils.Assert;
import com.mountain.common.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * <p> 自定义 AuthenticationManager </p>
 * 对访问的url进行权限认证处理
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/14 21:51
 **/
@Slf4j
public class CustomizeAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    public CustomizeAuthenticationManager(List<AuthenticationProvider> providers) {
        Assert.notEmpty(providers, () -> new BizException("providers list cannot be empty"));
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        for (AuthenticationProvider provider : providers) {
            if (!provider.supports(toTest)) {
                continue;
            }
            Authentication result = provider.authenticate(authentication);
            if (result != null) {
                copyDetails(authentication, result);
                return result;
            }
        }
        log.error("Authentication Failed");
        throw new ProviderNotFoundException(StrUtil.format("No AuthenticationProvider found for {}", toTest.getSimpleName()));
    }

    /**
     * Copies the authentication details from a source Authentication object to a
     * destination one, provided the latter does not already have one set.
     * @param source source authentication
     * @param dest the destination authentication object
     */
    private void copyDetails(Authentication source, Authentication dest) {
        if ((dest instanceof AbstractAuthenticationToken token) && (dest.getDetails() == null)) {
            token.setDetails(source.getDetails());
        }
    }

}
