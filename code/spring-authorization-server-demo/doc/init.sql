CREATE TABLE customized_server.oauth2_registered_client (
    id                             varchar(100)  NOT NULL,
    client_id                      varchar(100)  NOT NULL,
    client_id_issued_at            timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    client_secret                  varchar(200)  DEFAULT NULL,
    client_secret_expires_at       timestamp     DEFAULT NULL,
    client_name                    varchar(200)  NOT NULL,
    client_authentication_methods  varchar(1000) NOT NULL,
    authorization_grant_types      varchar(1000) NOT NULL,
    redirect_uris                  varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris      varchar(1000) DEFAULT NULL,
    scopes                         varchar(1000) NOT NULL,
    client_settings                varchar(2000) NOT NULL,
    token_settings                 varchar(2000) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE customized_server.oauth2_authorization_consent (
    registered_client_id           varchar(100)  NOT NULL,
    principal_name                 varchar(200)  NOT NULL,
    authorities                    varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
IMPORTANT:
    If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
    as PostgreSQL does not support the 'blob' data type.
*/
CREATE TABLE customized_server.oauth2_authorization (
    id                             varchar(100)  NOT NULL,
    registered_client_id           varchar(100)  NOT NULL,
    principal_name                 varchar(200)  NOT NULL,
    authorization_grant_type       varchar(100)  NOT NULL,
    authorized_scopes              varchar(1000) DEFAULT NULL,
    attributes                     blob          DEFAULT NULL,
    state                          varchar(500)  DEFAULT NULL,
    authorization_code_value       blob          DEFAULT NULL,
    authorization_code_issued_at   timestamp     DEFAULT NULL,
    authorization_code_expires_at  timestamp     DEFAULT NULL,
    authorization_code_metadata    blob          DEFAULT NULL,
    access_token_value             blob          DEFAULT NULL,
    access_token_issued_at         timestamp     DEFAULT NULL,
    access_token_expires_at        timestamp     DEFAULT NULL,
    access_token_metadata          blob          DEFAULT NULL,
    access_token_type              varchar(100)  DEFAULT NULL,
    access_token_scopes            varchar(1000) DEFAULT NULL,
    oidc_id_token_value            blob          DEFAULT NULL,
    oidc_id_token_issued_at        timestamp     DEFAULT NULL,
    oidc_id_token_expires_at       timestamp     DEFAULT NULL,
    oidc_id_token_metadata         blob          DEFAULT NULL,
    refresh_token_value            blob          DEFAULT NULL,
    refresh_token_issued_at        timestamp     DEFAULT NULL,
    refresh_token_expires_at       timestamp     DEFAULT NULL,
    refresh_token_metadata         blob          DEFAULT NULL,
    user_code_value                blob          DEFAULT NULL,
    user_code_issued_at            timestamp     DEFAULT NULL,
    user_code_expires_at           timestamp     DEFAULT NULL,
    user_code_metadata             blob          DEFAULT NULL,
    device_code_value              blob          DEFAULT NULL,
    device_code_issued_at          timestamp     DEFAULT NULL,
    device_code_expires_at         timestamp     DEFAULT NULL,
    device_code_metadata           blob          DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


1929d70f-a810-4082-a7dc-ad4d089c8989	messaging-client	2024-12-05 10:54:29.661501	{noop}secret	null	1929d70f-a810-4082-a7dc-ad4d089c8989	client_secret_basic	refresh_token,client_credentials,authorization_code	http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc	http://127.0.0.1:8080/logged-out	user.read,openid,profile,message.read,message.write	{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}	{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}
67aab7af-8241-4c2a-bcf1-a313ef0dd295	device-messaging-client	2024-12-05 10:54:29.745891	null	null	67aab7af-8241-4c2a-bcf1-a313ef0dd295	none	refresh_token,urn:ietf:params:oauth:grant-type:device_code			message.read,message.write	{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}	{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}









步骤一、未登录跳转到登录页: GET /oauth2/authorize
浏览器输入: http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc

流程: 走 spring-authorization-server Filter
1、AuthorizationFilter
(
    验证是否授权: 
        -> AuthorizationManager#authorize
        -> RequestMatcherDelegatingAuthorizationManager#check
        -> AuthenticatedAuthorizationManager#check
        -> AuthenticatedAuthorizationManager.AuthenticatedAuthorizationStrategy#isGranted
        -> AuthenticationTrustResolver#isAuthenticated【AnonymousAuthenticationToken, 未授权】
        -> throw new AuthorizationDeniedException("Access Denied", result);
        -> 到了 ExceptionTranslationFilter 的 Exception 处理逻辑
        -> ExceptionTranslationFilter#handleAccessDeniedException
        -> ExceptionTranslationFilter#sendStartAuthentication
        -> HttpSessionRequestCache#saveRequest
        -> request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest); 【保存请求； Saved request http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue to session】
        -> DelegatingAuthenticationEntryPoint#commence
        -> LoginUrlAuthenticationEntryPoint#commence
        -> redirectStrategy.sendRedirect(request, response, "http://auth-server:9000/login"); 【重定向到登录页: Redirecting to http://auth-server:9000/login】
)


步骤二、登录: POST /login

流程: 先走 spring-security Filter 进行认证授权, 再重定向到 GET /oauth2/authorize, 再重定向到 GET /oauth2/consent 进行授权确认 (如果有配置需要授权页)

1) POST /login
1、UsernamePasswordAuthenticationFilter
(
    登录认证授权:
        -> UsernamePasswordAuthenticationFilter#attemptAuthentication
        -> UsernamePasswordAuthenticationToken
        -> AbstractUserDetailsAuthenticationProvider#authenticate
        -> DaoAuthenticationProvider#retrieveUser
        -> UserDetailsService#loadUserByUsername
        -> DaoAuthenticationProvider#createSuccessAuthentication
        -> AbstractSessionFixationProtectionStrategy#onAuthentication【Changed session id from 3sLuvhBAH0jjoikLiNQrFGzH5QgDahodamVqPRwT】
        -> AbstractAuthenticationProcessingFilter#successfulAuthentication
        -> securityContextHolderStrategy.setContext(context)【将授权信息放入ThreadLocal】
        -> HttpSessionSecurityContextRepository#saveContext
        -> HttpSessionSecurityContextRepository#saveContextInHttpSession
        -> HttpSessionSecurityContextRepository#setContextInSession
        -> session.setAttribute("SPRING_SECURITY_CONTEXT", context); 【Stored SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=3sLuvhBAH0jjoikLiNQrFGzH5QgDahodamVqPRwT], Granted Authorities=[ROLE_USER]]] to HttpSession [io.undertow.servlet.spec.HttpSessionImpl@1a45fe4c]】
        -> RequestAttributeSecurityContextRepository#saveContext
        -> request.setAttribute("org.springframework.security.web.context.RequestAttributeSecurityContextRepository.SPRING_SECURITY_CONTEXT", context);
        -> SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess
        -> HttpSessionRequestCache#getRequest
        -> session.getAttribute("SPRING_SECURITY_SAVED_REQUEST"); 【获取第一步中保存的请求】
        -> getRedirectStrategy().sendRedirect(request, response, targetUrl);
        -> response.sendRedirect("http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue"); 【重定向到 GET /oauth2/authorize】
)

2) GET /oauth2/authorize
1、SecurityContextHolderFilter
(
    从session中获取SecurityContext:
        -> DelegatingSecurityContextRepository#loadDeferredContext
        -> HttpSessionSecurityContextRepository#loadDeferredContext【使用 SecurityContextHolder.getContext().getAuthentication() 时才会调用 HttpSessionSecurityContextRepository#readSecurityContextFromSession 方法】
        -> HttpSessionSecurityContextRepository#readSecurityContextFromSession
        -> httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        -> RequestAttributeSecurityContextRepository#loadDeferredContext【使用 SecurityContextHolder.getContext().getAuthentication() 时才会调用 RequestAttributeSecurityContextRepository#getContext 方法】
        -> request.getAttribute("org.springframework.security.web.context.RequestAttributeSecurityContextRepository.SPRING_SECURITY_CONTEXT"); 【如果上面拿到 SecurityContext 就不会走到这里了】
        -> SecurityContextHolder.getContext().getAuthentication() 后续在 OAuth2AuthorizationCodeRequestAuthenticationConverter#convert 中会调用
)
2、OAuth2AuthorizationEndpointFilter
(
    获取authorization code:
        -> OAuth2AuthorizationCodeRequestAuthenticationConverter#convert
        -> OAuth2AuthorizationCodeRequestAuthenticationToken
        -> OAuth2AuthorizationCodeRequestAuthenticationProvider#authenticate
        -> registeredClientRepository.findByClientId(authorizationCodeRequestAuthentication.getClientId()) 【根据 client_id 查询 RegisteredClient】
        -> authorizationConsentService.findById(registeredClient.getId(), principal.getName()) 【查询是否已进行授权，如果已进行过授权就不在进入授权页了】
        -> authorizationConsentRequired.test(authenticationContextBuilder.build()) 【判断是否需要进入授权页】
        -> 第一次进来：从来没有授权过的是需要进入授权页的
        -> authorizationService.save(authorization); 【保存 OAuth2Authorization, 包含 state】
        -> OAuth2AuthorizationConsentAuthenticationToken
        -> OAuth2AuthorizationEndpointFilter#sendAuthorizationConsent
        -> redirectStrategy.sendRedirect(request, response, "http://auth-server:9000/oauth2/consent?scope=message.read&client_id=messaging-client&state=kzvwJAGGrstfcQz-E7Ndslwx00VMT_UFaILQNnmlz-E%3D"); 【重定向到 GET /oauth2/consent, 这个是自定义授权页面】
        
        -> 第一次进来: 配置了不需要进入授权页 或者 第二次进来: 第一次已授权
        -> OAuth2AuthorizationCodeGenerator#generate 【生成AuthorizationCode, AuthorizationCode的过期时间可由 RegisteredClient.TokenSettings.Builder.authorizationCodeTimeToLive 设置】
        -> authorizationService.save(authorization); 【保存 OAuth2Authorization, 包含 authorization_code_value】
        -> OAuth2AuthorizationCodeRequestAuthenticationToken
        -> OAuth2AuthorizationEndpointFilter#sendAuthorizationResponse
        -> redirectStrategy.sendRedirect(request, response, "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc?code=8E2ImBPVLl8NDL3Rcw2LsSRMhI-1whYuJ27EGctxSllzPsTO5oe6cCI3Ztvp9gDf60RnWKN19FM_4kyyJo-4UB8Lk_YiSSRdvWN81RYtcFJGZGC6R5Yw1PC5y6wzvYRX"); 【重定向到最开始指定的页面】
)


步骤三、 自定义授权页面, 确实授权: POST /oauth2/authorize
在自定义授权页面, 点击需要的权限, 可以 确认授权/取消授权

确认授权流程如下:
1、SecurityContextHolderFilter
(
    从session中获取SecurityContext:
        -> DelegatingSecurityContextRepository#loadDeferredContext
        -> HttpSessionSecurityContextRepository#loadDeferredContext【使用 SecurityContextHolder.getContext().getAuthentication() 时才会调用 HttpSessionSecurityContextRepository#readSecurityContextFromSession 方法】
        -> HttpSessionSecurityContextRepository#readSecurityContextFromSession
        -> httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        -> RequestAttributeSecurityContextRepository#loadDeferredContext【使用 SecurityContextHolder.getContext().getAuthentication() 时才会调用 RequestAttributeSecurityContextRepository#getContext 方法】
        -> request.getAttribute("org.springframework.security.web.context.RequestAttributeSecurityContextRepository.SPRING_SECURITY_CONTEXT"); 【如果上面拿到 SecurityContext 就不会走到这里了】
        -> SecurityContextHolder.getContext().getAuthentication() 后续在 OAuth2AuthorizationConsentAuthenticationConverter#convert 中会调用
)
2、OAuth2AuthorizationEndpointFilter
(
    获取authorization code:
        -> OAuth2AuthorizationConsentAuthenticationConverter#convert
        -> OAuth2AuthorizationConsentAuthenticationToken
        -> OAuth2AuthorizationConsentAuthenticationProvider#authenticate
        -> authorizationService.findByToken(authorizationConsentAuthentication.getState(), STATE_TOKEN_TYPE) 【根据 state 查询 OAuth2Authorization】
        -> registeredClientRepository.findByClientId(authorizationConsentAuthentication.getClientId()) 【根据 client_id 查询 RegisteredClient】
        -> authorizationConsentService.findById(authorization.getRegisteredClientId(), authorization.getPrincipalName()) 【查询已授权信息, 如果有已授权信息就删除，否则直接保存授权信息, 这里可能涉及到授权信息的更新】
        -> OAuth2AuthorizationCodeGenerator#generate 【生成AuthorizationCode, AuthorizationCode的过期时间可由 RegisteredClient.TokenSettings.Builder.authorizationCodeTimeToLive 设置】
        -> authorizationService.save(updatedAuthorization); 【更新 OAuth2Authorization, 删除 state 保存 authorization_code_value】
        -> OAuth2AuthorizationCodeRequestAuthenticationToken
        -> OAuth2AuthorizationEndpointFilter#sendAuthorizationResponse
        -> redirectStrategy.sendRedirect(request, response, "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc?code=8E2ImBPVLl8NDL3Rcw2LsSRMhI-1whYuJ27EGctxSllzPsTO5oe6cCI3Ztvp9gDf60RnWKN19FM_4kyyJo-4UB8Lk_YiSSRdvWN81RYtcFJGZGC6R5Yw1PC5y6wzvYRX"); 【重定向到最开始指定的页面】
)


步骤四、获取Token: POST /oauth2/token
curl --location --request POST 'http://auth-server:9000/oauth2/token' \
--header 'Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=' \
--header 'Cookie: JSESSIONID=5u31t1gb6W6Xf_pyCSe7L2pT3lUCkTslR_Zwlv1e' \
--form 'grant_type="authorization_code"' \
--form 'redirect_uri="http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc"' \
--form 'code="ONY3_zbr6P8zUTOvfNxEIJ1evZQFXwpZIz9jOcz6pHpxrUhHxoh4PX_8JIhhm6xU5x4TknxsrPN7pmtSCvonfg_2vaiEezbpLfYhOEy8rCL1_nEiEpnneK53UHD1iPwL"'

流程:
1、OAuth2ClientAuthenticationFilter
(
    client_secret_basic验证登录: 
        -> ClientSecretBasicAuthenticationConverter#convert
        -> OAuth2ClientAuthenticationToken 
        -> ClientSecretAuthenticationProvider#authenticate
        -> OAuth2ClientAuthenticationToken
        -> OAuth2ClientAuthenticationFilter#onAuthenticationSuccess
        -> SecurityContextHolder.setContext(securityContext)
)
2、OAuth2TokenEndpointFilter
(
    生成access_token 和 refresh_token: 
        -> OAuth2AuthorizationCodeAuthenticationConverter#convert
        -> OAuth2AuthorizationCodeAuthenticationToken
        -> OAuth2AuthorizationCodeAuthenticationProvider#authenticate
        -> authorizationService.findByToken(authorizationCodeAuthentication.getCode(), AUTHORIZATION_CODE_TOKEN_TYPE) 【根据 authorization_code 查询 OAuth2Authorization】
        -> JwtGenerator#generate【生成access_token】 
        -> OAuth2RefreshTokenGenerator#generate【生成refresh_token】 
        -> OAuth2AccessTokenAuthenticationToken 
        -> OAuth2AccessTokenResponseAuthenticationSuccessHandler#onAuthenticationSuccess
        -> AbstractHttpMessageConverter#write
        -> OAuth2AccessTokenResponseHttpMessageConverter#writeInternal
        -> DefaultOAuth2AccessTokenResponseMapConverter#convert 【消息转换】
        -> jsonMessageConverter.write(tokenResponseParameters, STRING_OBJECT_MAP.getType(), MediaType.APPLICATION_JSON, outputMessage) 【返回json数据】
)









附录:

一、SecurityFilterChain
1、spring-security Filter

DisableEncodeUrlFilter, 
WebAsyncManagerIntegrationFilter, 
SecurityContextHolderFilter, 
HeaderWriterFilter, 
LogoutFilter, 
OAuth2AuthorizationRequestRedirectFilter, 
OAuth2LoginAuthenticationFilter, 
UsernamePasswordAuthenticationFilter, 
RequestCacheAwareFilter, 
SecurityContextHolderAwareRequestFilter, 
AnonymousAuthenticationFilter, 
ExceptionTranslationFilter, 
AuthorizationFilter



2、spring-authorization-server Filter

DisableEncodeUrlFilter
WebAsyncManagerIntegrationFilter
SecurityContextHolderFilter
AuthorizationServerContextFilter
HeaderWriterFilter
CsrfFilter
OidcLogoutEndpointFilter
LogoutFilter
OAuth2AuthorizationServerMetadataEndpointFilter
OAuth2AuthorizationEndpointFilter
OAuth2DeviceVerificationEndpointFilter
OidcProviderConfigurationEndpointFilter
NimbusJwkSetEndpointFilter
OAuth2ClientAuthenticationFilter
BearerTokenAuthenticationFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter
ExceptionTranslationFilter
AuthorizationFilter
OAuth2TokenEndpointFilter
OAuth2TokenIntrospectionEndpointFilter
OAuth2TokenRevocationEndpointFilter
OAuth2DeviceAuthorizationEndpointFilter
OidcUserInfoEndpointFilter


二、请求日志

[2024-12-03 18:51:01.253] [DEBUG] [24265] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc
[2024-12-03 18:51:01.343] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-03 18:51:01.352] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionCreatedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@5c215772]
[2024-12-03 18:51:01.354] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.s.web.savedrequest.HttpSessionRequestCache:80 : Saved request http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue to session
[2024-12-03 18:51:01.357] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:79 : Trying to match using MediaTypeRequestMatcher [contentNegotiationStrategy=org.springframework.web.accept.HeaderContentNegotiationStrategy@20c7e707, matchingMediaTypes=[text/html], useEquals=false, ignoredMediaTypes=[]]
[2024-12-03 18:51:01.358] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:82 : Match found! Executing org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint@394e7762
[2024-12-03 18:51:01.358] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/login
[2024-12-03 18:51:01.388] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /login
[2024-12-03 18:51:01.388] [TRACE] [24265] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:01.389] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:227 : Secured GET /login
[2024-12-03 18:51:01.765] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-03 18:51:01.822] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-03 18:51:01.823] [TRACE] [24265] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:01.824] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:227 : Secured GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-03 18:51:01.829] [DEBUG] [24265] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/css/signin.css
[2024-12-03 18:51:01.830] [TRACE] [24265] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:01.830] [DEBUG] [24265] --- [       XNIO-1 task-4] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/img/gitee.png
[2024-12-03 18:51:01.830] [DEBUG] [24265] --- [       XNIO-1 task-5] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/img/github.png
[2024-12-03 18:51:01.830] [DEBUG] [24265] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/css/signin.css
[2024-12-03 18:51:01.830] [TRACE] [24265] --- [       XNIO-1 task-4] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:01.830] [TRACE] [24265] --- [       XNIO-1 task-5] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:01.831] [DEBUG] [24265] --- [       XNIO-1 task-4] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/img/gitee.png
[2024-12-03 18:51:01.831] [DEBUG] [24265] --- [       XNIO-1 task-5] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/img/github.png
[2024-12-03 18:51:01.839] [DEBUG] [24265] --- [       XNIO-1 task-4] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-03 18:51:01.839] [DEBUG] [24265] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-03 18:51:01.839] [DEBUG] [24265] --- [       XNIO-1 task-5] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-03 18:51:01.844] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext



[2024-12-03 18:51:09.740] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing POST /login
[2024-12-03 18:51:09.741] [TRACE] [24265] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:09.888] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.authentication.dao.DaoAuthenticationProvider:199 : Authenticated user
[2024-12-03 18:51:09.889] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionIdChangedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@2b446d86]
[2024-12-03 18:51:09.889] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionIdChangedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@2b446d86]
[2024-12-03 18:51:09.889] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.a.s.ChangeSessionIdAuthenticationStrategy:98 : Changed session id from n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ
[2024-12-03 18:51:09.890] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.c.HttpSessionSecurityContextRepository:178 : Stored SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]] to HttpSession [io.undertow.servlet.spec.HttpSessionImpl@2b446d86]
[2024-12-03 18:51:09.890] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.a.UsernamePasswordAuthenticationFilter:327 : Set SecurityContextHolder to UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]
[2024-12-03 18:51:09.890] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue
[2024-12-03 18:51:09.894] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue
[2024-12-03 18:51:09.894] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]]
[2024-12-03 18:51:09.921] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/consent?scope=message.read&client_id=messaging-client&state=-Q0C9S71WGIvVlN1iH7veQmXipVtsugIfJShp48S_6s%3D
[2024-12-03 18:51:09.926] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/consent?scope=message.read&client_id=messaging-client&state=-Q0C9S71WGIvVlN1iH7veQmXipVtsugIfJShp48S_6s%3D
[2024-12-03 18:51:09.926] [TRACE] [24265] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:09.927] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:227 : Secured GET /oauth2/consent?scope=message.read&client_id=messaging-client&state=-Q0C9S71WGIvVlN1iH7veQmXipVtsugIfJShp48S_6s%3D
[2024-12-03 18:51:09.931] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]]
[2024-12-03 18:51:09.990] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-03 18:51:09.991] [TRACE] [24265] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-03 18:51:09.992] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:227 : Secured GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-03 18:51:09.997] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]]



[2024-12-03 18:51:18.142] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing POST /oauth2/authorize
[2024-12-03 18:51:18.143] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=n0JfkBWETIwdX4y1Pnk5gMc5e_t9-S_BAh79nfJZ], Granted Authorities=[ROLE_USER]]]
[2024-12-03 18:51:18.181] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc?code=T3_eyp93Vbqz4vbWJ2vCI50jdMQ_j62caT8oRF5FCIOIVHvbmfvgcfO_W7afGeqaFuIn3D37WcTxBzITIzRw383rkrBQTuxxrSvzGM8os7A9RbBFwaxTJNr9nqOHCDI6



[2024-12-03 18:51:36.523] [DEBUG] [24265] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing POST /oauth2/token
[2024-12-03 18:51:36.602] [DEBUG] [24265] --- [       XNIO-1 task-3] o.s.s.o.s.a.web.OAuth2ClientAuthenticationFilter:197 : Set SecurityContextHolder authentication to OAuth2ClientAuthenticationToken

