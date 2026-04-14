package com.mountain.web.configuration.security.customize;

import com.mountain.common.configuration.wrapper.MultiReadHttpServletRequestWrapper;
import com.mountain.common.utils.JacksonUtil;
import com.mountain.web.configuration.security.details.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * <p> 自定义 WebAuthenticationDetails </p>
 * <p> 扩展登录认证信息(手机验证码、登录终端类型） </p>
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/10 14:14
 **/
@Getter
public class CustomizeWebAuthenticationDetails extends WebAuthenticationDetails {

    private final UserLoginDTO userLogin;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public CustomizeWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        try {
            MultiReadHttpServletRequestWrapper wrappedRequest = new MultiReadHttpServletRequestWrapper(request);
            // 将前端传递的数据转换成jsonBean数据格式
            userLogin = JacksonUtil.toBean(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), UserLoginDTO.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
