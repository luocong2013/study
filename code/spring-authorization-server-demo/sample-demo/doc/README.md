## Spring Authorization Server 流程梳理

### 一、客户端（spring-boot-starter-oauth2-client）

#### 操作步骤一、未登录跳转到认证中心登录页（GET /）

> 浏览器输入：http://127.0.0.1:8080

日志如下：
```log
[2024-12-12 14:15:03.753] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /
[2024-12-12 14:15:03.768] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:03.804] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.web.savedrequest.HttpSessionRequestCache:80 : Saved request http://127.0.0.1:8080/?continue to session
[2024-12-12 14:15:03.810] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:79 : Trying to match using And [Not [RequestHeaderRequestMatcher [expectedHeaderName=X-Requested-With, expectedHeaderValue=XMLHttpRequest]], Not [And [Or [Ant [pattern='/login'], Ant [pattern='/favicon.ico']], And [Not [RequestHeaderRequestMatcher [expectedHeaderName=X-Requested-With, expectedHeaderValue=XMLHttpRequest]], MediaTypeRequestMatcher [contentNegotiationStrategy=org.springframework.web.accept.ContentNegotiationManager@2f2a41cd, matchingMediaTypes=[application/xhtml+xml, image/*, text/html, text/plain], useEquals=false, ignoredMediaTypes=[*/*]]]]], org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer$$Lambda$842/0x00000001314c90f8@5f46e2f]
[2024-12-12 14:15:03.811] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:82 : Match found! Executing org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint@6b8f9d3a
[2024-12-12 14:15:03.812] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://127.0.0.1:8080/oauth2/authorization/messaging-client-oidc
[2024-12-12 14:15:03.861] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorization/messaging-client-oidc
[2024-12-12 14:15:03.877] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=pinyo2TJoH0UVqa8Si3lUFmnz5d-ZjR4qfyDFCBkbtU%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=rLxY93NPq7isTNp4kyloAS5EGTdvWQjgzBEFwglQu8Q
[2024-12-12 14:15:03.886] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /
[2024-12-12 14:15:03.887] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:03.888] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.web.savedrequest.HttpSessionRequestCache:80 : Saved request http://127.0.0.1:8080/?continue to session
[2024-12-12 14:15:03.889] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:79 : Trying to match using And [Not [RequestHeaderRequestMatcher [expectedHeaderName=X-Requested-With, expectedHeaderValue=XMLHttpRequest]], Not [And [Or [Ant [pattern='/login'], Ant [pattern='/favicon.ico']], And [Not [RequestHeaderRequestMatcher [expectedHeaderName=X-Requested-With, expectedHeaderValue=XMLHttpRequest]], MediaTypeRequestMatcher [contentNegotiationStrategy=org.springframework.web.accept.ContentNegotiationManager@2f2a41cd, matchingMediaTypes=[application/xhtml+xml, image/*, text/html, text/plain], useEquals=false, ignoredMediaTypes=[*/*]]]]], org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer$$Lambda$842/0x00000001314c90f8@5f46e2f]
[2024-12-12 14:15:03.889] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:82 : Match found! Executing org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint@6b8f9d3a
[2024-12-12 14:15:03.889] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://127.0.0.1:8080/oauth2/authorization/messaging-client-oidc
[2024-12-12 14:15:03.894] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorization/messaging-client-oidc
[2024-12-12 14:15:03.896] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU
```

源码流程：
```java
整体流程：走 Client SecurityFilterChain 进行认证授权，再重定向到客户端配置的登录页，再重定向到认证中心进行认证授权（GET /oauth2/authorize）
1) GET /
1、AuthorizationFilter
(
    验证是否授权: 
        -> AuthorizationManager#authorize
        -> RequestMatcherDelegatingAuthorizationManager#check
        -> AuthenticatedAuthorizationManager#check
        -> AuthenticatedAuthorizationManager.AuthenticatedAuthorizationStrategy#isGranted
        -> AuthenticationTrustResolver#isAuthenticated【检查 {@link Authentication} 是否不为空、是否经过身份验证且非匿名。此时是 AnonymousAuthenticationToken (匿名)】
        -> throw new AuthorizationDeniedException("Access Denied", result);
        -> 到了 ExceptionTranslationFilter 的 Exception 处理逻辑
        -> ExceptionTranslationFilter#handleAccessDeniedException
        -> ExceptionTranslationFilter#sendStartAuthentication
        -> HttpSessionRequestCache#saveRequest
        -> request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest); 【保存请求； Saved request http://127.0.0.1:8080/?continue to session】
        -> LoginUrlAuthenticationEntryPoint#commence
        -> redirectStrategy.sendRedirect(request, response, redirectUrl); 【重定向到客户端配置的登录页: Redirecting to http://127.0.0.1:8080/oauth2/authorization/messaging-client-oidc】
)

2) GET /oauth2/authorization/messaging-client-oidc
1、OAuth2AuthorizationRequestRedirectFilter
(
    认证请求重定向到认证中心:
        -> OAuth2AuthorizationRequestRedirectFilter#doFilterInternal
        -> DefaultOAuth2AuthorizationRequestResolver#resolve【封装重定向到认证中心的请求】
        -> DefaultOAuth2AuthorizationRequestResolver#resolveRegistrationId【解析出 registrationId】
        -> DefaultOAuth2AuthorizationRequestResolver#resolve
        -> ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(registrationId);【根据 registrationId 查询 ClientRegistration】
        -> OAuth2AuthorizationRequestRedirectFilter#sendRedirectForAuthorization
        -> HttpSessionOAuth2AuthorizationRequestRepository#saveAuthorizationRequest
        -> request.getSession().setAttribute("org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository.AUTHORIZATION_REQUEST", authorizationRequest); 【将请求存入Session】
        -> this.authorizationRedirectStrategy.sendRedirect(request, response, authorizationRequest.getAuthorizationRequestUri());
        -> DefaultRedirectStrategy#sendRedirect【Redirecting to http://auth-server:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CpIype3kL3CQWGzVUVJYcUL3gN7EQyRLcgcBeXoj6-E%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=_17UHfUsakA-lk4mRNCstcuQ8RCH--Y6iPDObPNcj7o】
)
```

### 二、认证中心（spring-security-oauth2-authorization-server）

日志如下：
```log
[2024-12-12 14:15:03.929] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU
[2024-12-12 14:15:04.050] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:04.062] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionCreatedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@a2c09970]
[2024-12-12 14:15:04.068] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.web.savedrequest.HttpSessionRequestCache:80 : Saved request http://auth-server:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU&continue to session
[2024-12-12 14:15:04.075] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:79 : Trying to match using MediaTypeRequestMatcher [contentNegotiationStrategy=org.springframework.web.accept.HeaderContentNegotiationStrategy@152fdaf6, matchingMediaTypes=[text/html], useEquals=false, ignoredMediaTypes=[]]
[2024-12-12 14:15:04.076] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.DelegatingAuthenticationEntryPoint:82 : Match found! Executing org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint@5157e833
[2024-12-12 14:15:04.077] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/login
[2024-12-12 14:15:04.092] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /login
[2024-12-12 14:15:04.093] [TRACE] [54312] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:04.094] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /login
[2024-12-12 14:15:04.127] [ INFO] [54312] --- [       XNIO-1 task-2] sample.web.LoginController:34 : into login
[2024-12-12 14:15:04.495] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:04.588] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-12 14:15:04.590] [TRACE] [54312] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:04.591] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-12 14:15:04.598] [DEBUG] [54312] --- [       XNIO-1 task-5] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/img/gitee.png
[2024-12-12 14:15:04.598] [DEBUG] [54312] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/img/github.png
[2024-12-12 14:15:04.599] [TRACE] [54312] --- [       XNIO-1 task-5] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:04.599] [DEBUG] [54312] --- [       XNIO-1 task-4] org.springframework.security.web.FilterChainProxy:223 : Securing GET /assets/css/signin.css
[2024-12-12 14:15:04.600] [DEBUG] [54312] --- [       XNIO-1 task-5] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/img/gitee.png
[2024-12-12 14:15:04.600] [TRACE] [54312] --- [       XNIO-1 task-4] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:04.600] [TRACE] [54312] --- [       XNIO-1 task-3] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:04.602] [DEBUG] [54312] --- [       XNIO-1 task-4] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/css/signin.css
[2024-12-12 14:15:04.603] [DEBUG] [54312] --- [       XNIO-1 task-3] org.springframework.security.web.FilterChainProxy:227 : Secured GET /assets/img/github.png
[2024-12-12 14:15:04.610] [DEBUG] [54312] --- [       XNIO-1 task-3] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:04.610] [DEBUG] [54312] --- [       XNIO-1 task-4] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:04.611] [DEBUG] [54312] --- [       XNIO-1 task-5] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
[2024-12-12 14:15:04.613] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.AnonymousAuthenticationFilter:119 : Set SecurityContextHolder to anonymous SecurityContext
```

源码流程：
```java
整体流程: 走 Authorization Server SecurityFilterChain 重定向到认证中心的登录页
1）GET /oauth2/authorize（重定向到认证中心的登录页）
1、AuthorizationFilter
(
    验证是否授权:
        -> AuthorizationManager#authorize
        -> RequestMatcherDelegatingAuthorizationManager#check
        -> AuthenticatedAuthorizationManager#check
        -> AuthenticatedAuthorizationManager.AuthenticatedAuthorizationStrategy#isGranted
        -> AuthenticationTrustResolver#isAuthenticated【检查 {@link Authentication} 是否不为空、是否经过身份验证且非匿名。此时是 AnonymousAuthenticationToken (匿名)】
        -> throw new AuthorizationDeniedException("Access Denied", result);
        -> 到了 ExceptionTranslationFilter 的 Exception 处理逻辑
        -> ExceptionTranslationFilter#handleAccessDeniedException
        -> ExceptionTranslationFilter#sendStartAuthentication
        -> HttpSessionRequestCache#saveRequest
        -> request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest); 【保存请求； Saved request http://auth-server:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=message.read&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&continue to session】
        -> DelegatingAuthenticationEntryPoint#commence
        -> LoginUrlAuthenticationEntryPoint#commence
        -> redirectStrategy.sendRedirect(request, response, redirectUrl); 【重定向到认证中心的登录页: Redirecting to http://auth-server:9000/login】
)
```

### 三、认证中心-点击登录（spring-security-oauth2-authorization-server）

#### 操作步骤二、在认证中心登录页，输入用户名、密码点击登录

日志如下：
```log
[2024-12-12 14:15:10.104] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing POST /login
[2024-12-12 14:15:10.105] [TRACE] [54312] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:10.246] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.authentication.dao.DaoAuthenticationProvider:199 : Authenticated user
[2024-12-12 14:15:10.246] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionIdChangedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@8177ce0d]
[2024-12-12 14:15:10.246] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.session.HttpSessionEventPublisher:84 : Publishing event: org.springframework.security.web.session.HttpSessionIdChangedEvent[source=io.undertow.servlet.spec.HttpSessionImpl@8177ce0d]
[2024-12-12 14:15:10.247] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.s.ChangeSessionIdAuthenticationStrategy:98 : Changed session id from jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic
[2024-12-12 14:15:10.247] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:178 : Stored SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]] to HttpSession [io.undertow.servlet.spec.HttpSessionImpl@8177ce0d]
[2024-12-12 14:15:10.247] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.a.UsernamePasswordAuthenticationFilter:327 : Set SecurityContextHolder to UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]
[2024-12-12 14:15:10.248] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU&continue
[2024-12-12 14:15:10.251] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/authorize?response_type=code&client_id=messaging-client&scope=openid%20profile&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc&nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU&continue
[2024-12-12 14:15:10.252] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]]
[2024-12-12 14:15:10.280] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://auth-server:9000/oauth2/consent?scope=openid%20profile&client_id=messaging-client&state=ez2iS3g4vnkQHhvWp1UW2ic-eDC9kbnYXCCkG__LCqk%3D
[2024-12-12 14:15:10.284] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/consent?scope=openid%20profile&client_id=messaging-client&state=ez2iS3g4vnkQHhvWp1UW2ic-eDC9kbnYXCCkG__LCqk%3D
[2024-12-12 14:15:10.284] [TRACE] [54312] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:10.285] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /oauth2/consent?scope=openid%20profile&client_id=messaging-client&state=ez2iS3g4vnkQHhvWp1UW2ic-eDC9kbnYXCCkG__LCqk%3D
[2024-12-12 14:15:10.290] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]]
[2024-12-12 14:15:10.299] [ INFO] [54312] --- [       XNIO-1 task-2] sample.web.AuthorizationConsentController:59 : into consent
[2024-12-12 14:15:10.347] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-12 14:15:10.347] [TRACE] [54312] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:271 : Did not match request to Ant [pattern='/login/oauth2/code/*']
[2024-12-12 14:15:10.348] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /webjars/bootstrap/5.2.3/css/bootstrap.css
[2024-12-12 14:15:10.352] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]]
```

源码流程：
```java
整体流程：先走 Server SecurityFilterChain 进行认证授权，再重定向到 GET /oauth2/authorize（走 Authorization Server SecurityFilterChain）, 再重定向到 GET /oauth2/consent（走 Authorization Server SecurityFilterChain）进行授权确认（如果有配置需要授权页授权：requireAuthorizationConsent(true)）
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
        -> session.getAttribute("SPRING_SECURITY_SAVED_REQUEST"); 【获取上一步中保存的请求】
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
        -> 第一次进来：从来没有授权过的是需要进入授权页的（上面的日志就是走到了这里）
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
```

### 四、认证中心-点击确认授权（spring-security-oauth2-authorization-server）

#### 操作步骤三、在自定义授权页面，确认授权/取消授权

> 注意：只有 requireAuthorizationConsent(true) 配置为 true 才会有这一步操作

日志如下：
```log
[2024-12-12 14:23:03.384] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing POST /oauth2/authorize
[2024-12-12 14:23:03.386] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=jIq-yPJcYhTc_WE-ZX5Xb47Uz1TKxprF1a3zLwic], Granted Authorities=[ROLE_USER]]]
[2024-12-12 14:23:03.426] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.core.session.SessionRegistryImpl:134 : Registering session AjIvGR79RdJhbZTZGYzznDnit17oOzX6Xjpz4mlR, for principal org.springframework.security.core.userdetails.User [Username=user1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]]
[2024-12-12 14:23:03.427] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc?code=f--ELVQCpeIRLlpiUprQKy_qKaGA1hKYx-ScfJRBOqxHb9KbmzFwek9Okxw3adz9qKztZo8bvY7CW1mN5-Ki7ScdOKOchD8BLx6P-PiBjzEbPfPxzNaBmTo9bAS_mlLN&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D
```

源码流程：
```java
1）POST /oauth2/authorize
整体流程（确认授权）：走 Authorization Server SecurityFilterChain 进行授权绑定
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
        -> redirectStrategy.sendRedirect(request, response, "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc?code=8E2ImBPVLl8NDL3Rcw2LsSRMhI-1whYuJ27EGctxSllzPsTO5oe6cCI3Ztvp9gDf60RnWKN19FM_4kyyJo-4UB8Lk_YiSSRdvWN81RYtcFJGZGC6R5Yw1PC5y6wzvYRX"); 【重定向到客户端指定的redirect_uri】
)
```

### 五、认证中心处理完毕后，客户端再接着处理（spring-boot-starter-oauth2-client）

日志如下：
```log
[2024-12-12 14:23:03.435] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /login/oauth2/code/messaging-client-oidc?code=f--ELVQCpeIRLlpiUprQKy_qKaGA1hKYx-ScfJRBOqxHb9KbmzFwek9Okxw3adz9qKztZo8bvY7CW1mN5-Ki7ScdOKOchD8BLx6P-PiBjzEbPfPxzNaBmTo9bAS_mlLN&state=CsT3f2lv2Df-X3VEOv8OH_BG-9FS4NKFcqSn9noipW8%3D

[2024-12-12 14:23:03.441] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : HTTP POST http://auth-server:9000/oauth2/token
[2024-12-12 14:23:03.441] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Accept=[application/json, application/*+json]
[2024-12-12 14:23:03.442] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Writing [{grant_type=[authorization_code], code=[f--ELVQCpeIRLlpiUprQKy_qKaGA1hKYx-ScfJRBOqxHb9KbmzFwek9Okxw3adz9qKztZo8bvY7CW1mN5-Ki7ScdOKOchD8BLx6P-PiBjzEbPfPxzNaBmTo9bAS_mlLN], redirect_uri=[http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc]}] as "application/x-www-form-urlencoded;charset=UTF-8"
[2024-12-12 14:23:03.631] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Response 200 OK
[2024-12-12 14:23:03.632] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Reading to [org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse] as "application/json;charset=UTF-8"

[2024-12-12 14:23:03.752] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : HTTP GET http://auth-server:9000/oauth2/jwks
[2024-12-12 14:23:03.753] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Accept=[text/plain, application/json, application/*+json, */*]
[2024-12-12 14:23:03.763] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Response 200 OK
[2024-12-12 14:23:03.764] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Reading to [java.lang.String] as "application/json;charset=ISO-8859-1"

[2024-12-12 14:23:03.787] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : HTTP GET http://auth-server:9000/userinfo
[2024-12-12 14:23:03.788] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Accept=[application/json, application/*+json]
[2024-12-12 14:51:20.956] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Response 200 OK
[2024-12-12 14:51:20.956] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.client.RestTemplate:127 : Reading to [java.util.Map<java.lang.String, java.lang.Object>]
[2024-12-12 14:51:31.295] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.a.s.ChangeSessionIdAuthenticationStrategy:98 : Changed session id from 7JAZMKA8GaW0Nzxf1uWXrIA9VVxC3nxOhQdxzlDm
[2024-12-12 14:51:31.297] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:178 : Stored SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [user1], Granted Authorities: [[OIDC_USER, SCOPE_openid, SCOPE_profile]], User Attributes: [{sub=user1, aud=[messaging-client], azp=messaging-client, auth_time=2024-12-12T06:23:03Z, iss=http://auth-server:9000, exp=2024-12-12T06:53:03Z, iat=2024-12-12T06:23:03Z, nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU, jti=6d0b6387-d0df-49a3-98d5-caf96925e861, sid=53_tM6V8Ra_k0odOO2v0IKjj0OutDjGqQ0pbNEIicCQ}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=7JAZMKA8GaW0Nzxf1uWXrIA9VVxC3nxOhQdxzlDm], Granted Authorities=[OIDC_USER, SCOPE_openid, SCOPE_profile]]] to HttpSession [io.undertow.servlet.spec.HttpSessionImpl@277c6deb]
[2024-12-12 14:51:31.297] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.o.client.web.OAuth2LoginAuthenticationFilter:327 : Set SecurityContextHolder to OAuth2AuthenticationToken [Principal=Name: [user1], Granted Authorities: [[OIDC_USER, SCOPE_openid, SCOPE_profile]], User Attributes: [{sub=user1, aud=[messaging-client], azp=messaging-client, auth_time=2024-12-12T06:23:03Z, iss=http://auth-server:9000, exp=2024-12-12T06:53:03Z, iat=2024-12-12T06:23:03Z, nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU, jti=6d0b6387-d0df-49a3-98d5-caf96925e861, sid=53_tM6V8Ra_k0odOO2v0IKjj0OutDjGqQ0pbNEIicCQ}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=7JAZMKA8GaW0Nzxf1uWXrIA9VVxC3nxOhQdxzlDm], Granted Authorities=[OIDC_USER, SCOPE_openid, SCOPE_profile]]
[2024-12-12 14:51:31.297] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.security.web.DefaultRedirectStrategy:61 : Redirecting to http://127.0.0.1:8080/?continue
[2024-12-12 14:51:31.302] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /?continue
[2024-12-12 14:51:31.303] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.web.savedrequest.HttpSessionRequestCache:130 : Loaded matching saved request http://127.0.0.1:8080/?continue
[2024-12-12 14:51:31.305] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [user1], Granted Authorities: [[OIDC_USER, SCOPE_openid, SCOPE_profile]], User Attributes: [{sub=user1, aud=[messaging-client], azp=messaging-client, auth_time=2024-12-12T06:23:03Z, iss=http://auth-server:9000, exp=2024-12-12T06:53:03Z, iat=2024-12-12T06:23:03Z, nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU, jti=6d0b6387-d0df-49a3-98d5-caf96925e861, sid=53_tM6V8Ra_k0odOO2v0IKjj0OutDjGqQ0pbNEIicCQ}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=7JAZMKA8GaW0Nzxf1uWXrIA9VVxC3nxOhQdxzlDm], Granted Authorities=[OIDC_USER, SCOPE_openid, SCOPE_profile]]]
[2024-12-12 14:51:31.305] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /?continue
[2024-12-12 14:51:31.311] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.servlet.DispatcherServlet:120 : GET "/?continue", parameters={masked}
[2024-12-12 14:51:31.312] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.w.s.m.m.a.RequestMappingHandlerMapping:533 : Mapped to sample.web.DefaultController#root()
[2024-12-12 14:51:31.334] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.servlet.view.RedirectView:307 : View name [redirect:], model {}
[2024-12-12 14:51:31.335] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.servlet.DispatcherServlet:1138 : Completed 302 FOUND
[2024-12-12 14:51:31.340] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /index
[2024-12-12 14:51:31.341] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.s.w.c.HttpSessionSecurityContextRepository:234 : Retrieved SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [user1], Granted Authorities: [[OIDC_USER, SCOPE_openid, SCOPE_profile]], User Attributes: [{sub=user1, aud=[messaging-client], azp=messaging-client, auth_time=2024-12-12T06:23:03Z, iss=http://auth-server:9000, exp=2024-12-12T06:53:03Z, iat=2024-12-12T06:23:03Z, nonce=jq2Fx26WTvz8Ws8uNXo4y5mt6_vMogvzZiT0KqrhKWU, jti=6d0b6387-d0df-49a3-98d5-caf96925e861, sid=53_tM6V8Ra_k0odOO2v0IKjj0OutDjGqQ0pbNEIicCQ}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=7JAZMKA8GaW0Nzxf1uWXrIA9VVxC3nxOhQdxzlDm], Granted Authorities=[OIDC_USER, SCOPE_openid, SCOPE_profile]]]
[2024-12-12 14:51:31.341] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:227 : Secured GET /index
[2024-12-12 14:51:31.342] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.servlet.DispatcherServlet:120 : GET "/index", parameters={}
[2024-12-12 14:51:31.342] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.w.s.m.m.a.RequestMappingHandlerMapping:533 : Mapped to sample.web.DefaultController#index()
[2024-12-12 14:51:31.346] [DEBUG] [54321] --- [       XNIO-1 task-2] o.s.w.servlet.view.ContentNegotiatingViewResolver:354 : Selected 'text/html' given [text/html, application/xhtml+xml, image/avif, image/webp, image/apng, application/xml;q=0.9, */*;q=0.8, application/signed-exchange;v=b3;q=0.7]
[2024-12-12 14:51:31.704] [DEBUG] [54321] --- [       XNIO-1 task-2] org.springframework.web.servlet.DispatcherServlet:1138 : Completed 200 OK
```

源码流程：
```java
1）GET /login/oauth2/code/*
整体流程：走 Client SecurityFilterChain，封装请求获取token，获取 JWKSet 密钥集，获取用户信息，最后重定向到 操作步骤一中在浏览器输入的地址
1、OAuth2LoginAuthenticationFilter
(
    客户端登录:
        -> AbstractAuthenticationProcessingFilter#doFilter
        -> OAuth2LoginAuthenticationFilter#attemptAuthentication
        -> OAuth2LoginAuthenticationToken authenticationRequest = new OAuth2LoginAuthenticationToken(clientRegistration, new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse));
        -> OAuth2LoginAuthenticationProvider#authenticate 【 scopes 不包含 openid 走这个 provider 】
        -> OidcAuthorizationCodeAuthenticationProvider#authenticate 【 scopes 包含 openid 走这个 provider 】
        -> OidcAuthorizationCodeAuthenticationProvider#getResponse
        -> DefaultAuthorizationCodeTokenResponseClient#getTokenResponse
        -> ClientAuthenticationMethodValidatingRequestEntityConverter#convert
        -> AbstractOAuth2AuthorizationGrantRequestEntityConverter#convert
        -> DefaultOAuth2TokenRequestHeadersConverter#convert【获取header】
        -> OAuth2AuthorizationCodeGrantRequestEntityConverter#createParameters【获取参数】
        -> DefaultAuthorizationCodeTokenResponseClient#getResponse【HTTP POST http://auth-server:9000/oauth2/token  获取token】
        -> OidcAuthorizationCodeAuthenticationProvider#createOidcToken
        -> OidcIdTokenDecoderFactory#createDecoder
        -> OidcAuthorizationCodeAuthenticationProvider#getJwt
        -> NimbusJwtDecoder#decode
        -> NimbusJwtDecoder#parse
        -> NimbusJwtDecoder#createJwt
        -> DefaultJWTProcessor#process
        -> DefaultJWTProcessor#selectKeys
        -> JWSVerificationKeySelector#selectJWSKeys
        -> RemoteJWKSet#get【List<JWK> jwkMatches = getJWKSource().get(new JWKSelector(jwkMatcher), context);】
        -> RemoteJWKSet#updateJWKSetFromURL
        -> NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder.RestOperationsResourceRetriever#retrieveResource
        -> NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder.RestOperationsResourceRetriever#getResponse【HTTP GET http://auth-server:9000/oauth2/jwks 获取 JWKSet 密钥集】
        -> OidcUser oidcUser = this.userService.loadUser(new OidcUserRequest(clientRegistration, accessTokenResponse.getAccessToken(), idToken, additionalParameters));
        -> OidcUserService#loadUser
        -> DefaultOAuth2UserService#loadUser
        -> OAuth2UserRequestEntityConverter#convert
        -> DefaultOAuth2UserService#getResponse【HTTP GET http://auth-server:9000/userinfo】
        -> OidcUserRequestUtils#getUser
        -> return DefaultOidcUser
        -> return OAuth2LoginAuthenticationToken
        -> OAuth2AuthenticationToken oauth2Authentication = this.authenticationResultConverter.convert(authenticationResult);【将 OAuth2LoginAuthenticationToken 转换为 OAuth2AuthenticationToken】
        -> this.authorizedClientRepository.saveAuthorizedClient(authorizedClient, oauth2Authentication, request, response);【存储客户端认证信息】
        -> AbstractAuthenticationProcessingFilter#successfulAuthentication【认证成功后的处理】
        -> this.securityContextHolderStrategy.setContext(context);【认证信息存入ThreadLocal】
        -> this.securityContextRepository.saveContext(context, request, response);
        -> DelegatingSecurityContextRepository#saveContext
        -> HttpSessionSecurityContextRepository#saveContext
        -> HttpSessionSecurityContextRepository#saveContextInHttpSession
        -> HttpSessionSecurityContextRepository#setContextInSession
        -> session.setAttribute("SPRING_SECURITY_CONTEXT", context);【Stored SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [user1], Granted Authorities: [[OIDC_USER, SCOPE_openid, SCOPE_profile]], User Attributes: [{sub=user1, aud=[messaging-client], azp=messaging-client, auth_time=2024-12-12T03:19:48Z, iss=http://auth-server:9000, exp=2024-12-12T03:49:49Z, iat=2024-12-12T03:19:49Z, nonce=ugV4rZH8wCW0tWFu_c7Xf_OZOMpxgReBrQwe-FN2dKE, jti=c95d782f-1442-42f0-bb5b-cbc16a7dac50, sid=9-ZFikzi6DEsp5zUMxnfVoffjxdYwbR9Ctht2Ke3iJs}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=MGGe-gAo3Ikoa752Sd5Q2974RSex7yP82S891DwW], Granted Authorities=[OIDC_USER, SCOPE_openid, SCOPE_profile]]] to HttpSession [io.undertow.servlet.spec.HttpSessionImpl@62a2b33c]】
        -> RequestAttributeSecurityContextRepository#saveContext
        -> request.setAttribute("org.springframework.security.web.context.RequestAttributeSecurityContextRepository.SPRING_SECURITY_CONTEXT", context);
        -> this.successHandler.onAuthenticationSuccess(request, response, authResult);
        -> SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess
        -> getRedirectStrategy().sendRedirect(request, response, targetUrl);【Redirecting to http://127.0.0.1:8080/?continue】
        -> GET /?continue
        -> redirect:/index
        -> GET /index
        -> index.html
)
```

### 六、认证中心-生成token（spring-security-oauth2-authorization-server）

日志如下：
```log
[2024-12-12 14:23:03.449] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing POST /oauth2/token
[2024-12-12 14:23:03.532] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.o.s.a.web.OAuth2ClientAuthenticationFilter:197 : Set SecurityContextHolder authentication to OAuth2ClientAuthenticationToken
```

源码流程：
```java
1）POST /oauth2/token
整体流程：
curl --location --request POST 'http://auth-server:9000/oauth2/token' \
--header 'Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=' \
--header 'Cookie: JSESSIONID=5u31t1gb6W6Xf_pyCSe7L2pT3lUCkTslR_Zwlv1e' \
--form 'grant_type="authorization_code"' \
--form 'redirect_uri="http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc"' \
--form 'code="ONY3_zbr6P8zUTOvfNxEIJ1evZQFXwpZIz9jOcz6pHpxrUhHxoh4PX_8JIhhm6xU5x4TknxsrPN7pmtSCvonfg_2vaiEezbpLfYhOEy8rCL1_nEiEpnneK53UHD1iPwL"'

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
```

### 七、认证中心-获取 JWKSet 密钥集（spring-security-oauth2-authorization-server）

日志如下：
```log
[2024-12-12 14:23:03.757] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /oauth2/jwks
```

源码流程：
```java
1）GET /oauth2/jwks
1、NimbusJwkSetEndpointFilter
(
    获取 JWKSet 密钥集:
        -> NimbusJwkSetEndpointFilter#doFilterInternal
)
```

### 八、认证中心-获取用户信息（spring-security-oauth2-authorization-server）

日志如下：
```log
[2024-12-12 14:23:03.791] [DEBUG] [54312] --- [       XNIO-1 task-2] org.springframework.security.web.FilterChainProxy:223 : Securing GET /userinfo
[2024-12-12 14:23:03.806] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.o.s.r.a.JwtAuthenticationProvider:93 : Authenticated token
[2024-12-12 14:23:03.807] [DEBUG] [54312] --- [       XNIO-1 task-2] o.s.s.o.s.r.w.a.BearerTokenAuthenticationFilter:143 : Set SecurityContextHolder to JwtAuthenticationToken [Principal=org.springframework.security.oauth2.jwt.Jwt@6a234507, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=null], Granted Authorities=[SCOPE_openid, SCOPE_profile]]
```

源码流程：
```java
1）GET /userinfo
1、NimbusJwkSetEndpointFilter
1、OidcUserInfoEndpointFilter
(
    获取用户信息:
        -> OidcUserInfoEndpointFilter#doFilterInternal
        -> DelegatingAuthenticationConverter#convert
        -> OidcUserInfoEndpointConfigurer#createDefaultAuthenticationConverters
        -> OidcUserInfoAuthenticationToken
        -> ProviderManager#authenticate
        -> OidcUserInfoAuthenticationProvider#authenticate
        -> OidcUserInfo userInfo = this.userInfoMapper.apply(authenticationContext);
        -> OidcUserInfoAuthenticationProvider.DefaultOidcUserInfoMapper#apply
        -> OidcUserInfoAuthenticationProvider.DefaultOidcUserInfoMapper#getClaimsRequestedByScope
        -> return new OidcUserInfo(scopeRequestedClaims);
        -> OidcUserInfoAuthenticationToken
        -> OidcUserInfoEndpointFilter#sendUserInfoResponse
        -> AbstractHttpMessageConverter#write
        -> OidcUserInfoHttpMessageConverter#writeInternal
        -> this.jsonMessageConverter.write(userInfoResponseParameters, STRING_OBJECT_MAP.getType(), MediaType.APPLICATION_JSON, outputMessage);【返回json数据】
)
```



### 附录

#### Client SecurityFilterChain

> 即客户端的 securityFilterChain

```java
DisableEncodeUrlFilter
WebAsyncManagerIntegrationFilter
SecurityContextHolderFilter
HeaderWriterFilter
CsrfFilter
LogoutFilter
OAuth2AuthorizationRequestRedirectFilter
OAuth2AuthorizationRequestRedirectFilter
OAuth2LoginAuthenticationFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter
OAuth2AuthorizationCodeGrantFilter
ExceptionTranslationFilter
AuthorizationFilter
```


#### Server SecurityFilterChain

> 即认证中心的 defaultSecurityFilterChain

```java
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
```

#### Authorization Server SecurityFilterChain

> 即认证中心的 authorizationServerSecurityFilterChain

```java
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
```

