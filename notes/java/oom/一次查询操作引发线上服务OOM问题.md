## 一次查询操作引发线上服务OOM问题



### 一、CRM系统日志

```java
2022-08-19 12:04:27,421 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.c.security.filter.AuthenticationFilter:132 - Rest come in, uri:/api/rest/xy-crm/external/inquire/sn/v1, remote ip:117.174.81.161, headers:{referer=https://cdn.xylink.com/h5/crm/index.html?sdkjshdksdadsdsd, sec-fetch-site=same-site, x5-uuid=e78263b3c3cc3aeee9487df86619e9a5, origin=https://cdn.xylink.com, x-forwarded-port=443, x-client-ip=117.174.81.161, authorization=eyJhbGciOiJIUzUxMiJ9.eyJyb2xlX2xvZ2luIjoiW-aVsOaNruadg-mZkF0iLCJzdWIiOiJ5YW5namlhIiwiaWF0IjoxNjYwODgxMzMwLCJleHAiOjE2NjA4ODg1MzB9.jEPT5lywrWTEdkXxQFBdW2PqqmpOSSzoQkthFXEjHHHUofzSQHX0x-0HTvGWNlwnWFM0WGxoYHSsusKN4eDxfw, x-true-ip=117.174.81.161, x-forwarded-host=crm-api.xylink.com, host=crm-api.xylink.com, x-request-id=045a2c1ea9a50363a86046b005b1bac4, sec-fetch-mode=cors, web-server-type=nginx, x-forwarded-proto=https, accept-language=zh-CN,zh;q=0.9, wl-proxy-client-ip=117.174.81.161, x-forwarded-cluster=waf,, x-forwarded-for=123.57.117.133, eagleeye-traceid=7b39758516608818674168219ea3ba, accept=application/json, text/plain, */*, x-real-ip=123.57.117.133, x-original-forwarded-for=117.174.81.161, x-scheme=https, eagleeye-rpcid=0.1, accept-encoding=gzip, deflate, br, user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36, sec-fetch-dest=empty}
2022-08-19 12:04:27,421 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.c.security.filter.AuthenticationFilter:66 - request uri:/api/rest/xy-crm/external/inquire/sn/v1 method:GET content-type：null
2022-08-19 12:04:27,422 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.c.security.filter.AuthenticationFilter:144 - `/api/rest/xy-crm/external/inquire/sn/v1` get param: 
2022-08-19 12:04:27,422 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.c.c.security.filter.AuthenticationFilter:96 - check token:eyJhbGciOiJIUzUxMiJ9.eyJyb2xlX2xvZ2luIjoiW-aVsOaNruadg-mZkF0iLCJzdWIiOiJ5YW5namlhIiwiaWF0IjoxNjYwODgxMzMwLCJleHAiOjE2NjA4ODg1MzB9.jEPT5lywrWTEdkXxQFBdW2PqqmpOSSzoQkthFXEjHHHUofzSQHX0x-0HTvGWNlwnWFM0WGxoYHSsusKN4eDxfw
2022-08-19 12:04:27,422 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] com.xylink.crm.common.utils.JwtUtil:39 - current username: yangjia
2022-08-19 12:04:27,423 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] com.xylink.crm.basic.mapper.UserMapper.selectOne:137 - ==>  Preparing: SELECT id,username,password,account_type,full_name AS name,major_department_id,user_position AS position,role_id,phone,phone_verify_state,email,sex,is_enable AS enable,user_status,recent_login_time,recent_login_ip,ip_city,u8_user_code,u8_sync_time,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM `basic_user` WHERE deleted=0 AND (username = ? OR phone = ?)
2022-08-19 12:04:27,423 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] com.xylink.crm.basic.mapper.UserMapper.selectOne:137 - ==> Parameters: yangjia(String), yangjia(String)
2022-08-19 12:04:27,424 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] com.xylink.crm.basic.mapper.UserMapper.selectOne:137 - <==      Total: 1
2022-08-19 12:04:27,425 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.crm.basic.service.impl.PermissionServiceImpl:180 - list data permission of:13847072252887040
2022-08-19 12:04:27,425 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.DataPermissionDictMapper.selectList:137 - ==>  Preparing: SELECT id,db_table_name,alias_name,owner_rule_default,query_permission_default,modify_permission_default,delete_permission_default,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_data_permission_dict WHERE deleted=0
2022-08-19 12:04:27,425 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.DataPermissionDictMapper.selectList:137 - ==> Parameters: 
2022-08-19 12:04:27,426 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.DataPermissionDictMapper.selectList:137 - <==      Total: 20
2022-08-19 12:04:27,427 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.RoleDataPermissionMapper.selectList:137 - ==>  Preparing: SELECT id,role_id,db_table_name,alias_name,owner_rule,query_permission,modify_permission,delete_permission,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_role_data_permission WHERE role_id=? AND deleted=0
2022-08-19 12:04:27,427 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.RoleDataPermissionMapper.selectList:137 - ==> Parameters: 13847072252887040(Long)
2022-08-19 12:04:27,428 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.b.mapper.RoleDataPermissionMapper.selectList:137 - <==      Total: 20
2022-08-19 12:04:27,429 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.UserDeptMapper.selectList:137 - ==>  Preparing: SELECT id,user_id,department_id,department_code,is_major_dept,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_user_department WHERE user_id=? AND deleted=0
2022-08-19 12:04:27,429 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.UserDeptMapper.selectList:137 - ==> Parameters: 13525285300338600(Long)
2022-08-19 12:04:27,430 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.UserDeptMapper.selectList:137 - <==      Total: 4
2022-08-19 12:04:27,430 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.basic.mapper.DepartmentMapper.selectBatchIds:137 - ==>  Preparing: SELECT id,parent_id,department_name,department_code,charger_id,distributor_id,channel_manager_id,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM `basic_department` WHERE id IN ( ? , ? , ? , ? ) AND deleted=0
2022-08-19 12:04:27,430 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.basic.mapper.DepartmentMapper.selectBatchIds:137 - ==> Parameters: 1000047(Long), 16070019998814208(Long), 1000004(Long), 1000003(Long)
2022-08-19 12:04:27,431 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.basic.mapper.DepartmentMapper.selectBatchIds:137 - <==      Total: 4
2022-08-19 12:04:27,431 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.service.impl.ResellerNotRenewedServiceImpl:54 - verify renew for user id 13525285300338600
2022-08-19 12:04:27,431 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.service.impl.ResellerNotRenewedServiceImpl:56 - user 13525285300338600 is internal staff
2022-08-19 12:04:27,431 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.crm.basic.service.impl.PermissionServiceImpl:65 - GET listRoleCode of:/external/inquire/sn/v1
2022-08-19 12:04:27,432 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.xylink.crm.basic.mapper.ResourceMapper.selectOne:137 - ==>  Preparing: SELECT id,parent_id,resource_name,resource_code,resource_type,url,request_type,authorize_type,sort_no,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_resource WHERE url=? AND request_type=? AND deleted=0
2022-08-19 12:04:27,433 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.xylink.crm.basic.mapper.ResourceMapper.selectOne:137 - ==> Parameters: /external/inquire/sn/v1(String), GET(String)
2022-08-19 12:04:27,433 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.xylink.crm.basic.mapper.ResourceMapper.selectOne:137 - <==      Total: 1
2022-08-19 12:04:27,434 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.crm.basic.service.impl.PermissionServiceImpl:158 - start listRoleCode
2022-08-19 12:04:27,434 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.ResourceMapper.selectById:137 - ==>  Preparing: SELECT id,parent_id,resource_name,resource_code,resource_type,url,request_type,authorize_type,sort_no,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_resource WHERE id=? AND deleted=0
2022-08-19 12:04:27,434 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.ResourceMapper.selectById:137 - ==> Parameters: 117(Long)
2022-08-19 12:04:27,435 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.ResourceMapper.selectById:137 - <==      Total: 1
2022-08-19 12:04:27,435 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.crm.basic.service.impl.PermissionServiceImpl:158 - start listRoleCode
2022-08-19 12:04:27,436 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.RoleResourceMapper.selectList:137 - ==>  Preparing: SELECT id,role_id,resource_code,gmt_create,gmt_modified,creator_id,modifier_id,remark FROM basic_role_resource WHERE resource_code=? AND deleted=0
2022-08-19 12:04:27,436 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.RoleResourceMapper.selectList:137 - ==> Parameters: inquire(String)
2022-08-19 12:04:27,441 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.crm.basic.mapper.RoleResourceMapper.selectList:137 - <==      Total: 14
2022-08-19 12:04:27,444 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.c.security.interceptor.ReferInterceptor:53 - Refer check uri:/api/rest/xy-crm/external/inquire/sn/v1
2022-08-19 12:04:27,450 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] com.zaxxer.hikari.HikariDataSource:110 - sqlserver - Starting...
2022-08-19 12:04:27,546 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] com.zaxxer.hikari.HikariDataSource:123 - sqlserver - Start completed.
2022-08-19 12:04:27,546 [http-nio2-8080-exec-43] DEBUG [16608818674214049235645] c.x.c.i.mapper.InquireU8Mapper.inquireDataBySn:137 - ==>  Preparing: select top 1 c.cinvsn code, so.cdefine10 orderCode, inv.cInvCode productCode, inv.cinvname productName from dbo.rdrecords32 a left join dbo.ST_SNDetail_SaleOut c on a.autoid = c.ivouchsid left join dbo.SO_SODetails sos on sos.isosid = a.iorderdid inner join dbo.SO_SOMain so on sos.id = so.id left join dbo.inventory inv on inv.cinvcode = a.cinvcode where c.cinvsn = ? order by so.ddate desc
2022-08-19 12:04:30,184 [scheduling-1] INFO  [] c.x.crm.common.configuration.mybatis.MachineIdUtil:115 - xy-crm-prd-755797df99-nbbbq keep alive snow_id:0,failCount:0
2022-08-19 12:04:30,188 [http-nio2-8080-exec-43] ERROR [16608818674214049235645] c.x.c.c.configuration.aop.GlobalExceptionHandler:190 - systemErrorHandler error, URI: /api/rest/xy-crm/external/inquire/sn/v1
org.springframework.web.util.NestedServletException: Handler dispatch failed; nested exception is java.lang.OutOfMemoryError: Metaspace
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1082)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:223)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:217)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:122)
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:116)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:109)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at com.xylink.crm.common.configuration.security.filter.AuthenticationFilter.doFilterInternal(AuthenticationFilter.java:111)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:223)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:217)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)
	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:112)
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:82)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211)
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183)
	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354)
	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:94)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.boot.web.servlet.support.ErrorPageFilter.doFilter(ErrorPageFilter.java:126)
	at org.springframework.boot.web.servlet.support.ErrorPageFilter.access$000(ErrorPageFilter.java:64)
	at org.springframework.boot.web.servlet.support.ErrorPageFilter$1.doFilterInternal(ErrorPageFilter.java:101)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.springframework.boot.web.servlet.support.ErrorPageFilter.doFilter(ErrorPageFilter.java:119)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:196)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
	at org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:698)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:364)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:624)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:831)
	at org.apache.tomcat.util.net.Nio2Endpoint$SocketProcessor.doRun(Nio2Endpoint.java:1638)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
	at org.apache.tomcat.util.net.AbstractEndpoint.processSocket(AbstractEndpoint.java:1125)
	at org.apache.tomcat.util.net.Nio2Endpoint$Nio2SocketWrapper$2.completed(Nio2Endpoint.java:587)
	at org.apache.tomcat.util.net.Nio2Endpoint$Nio2SocketWrapper$2.completed(Nio2Endpoint.java:565)
	at sun.nio.ch.Invoker.invokeUnchecked(Invoker.java:126)
	at sun.nio.ch.Invoker$2.run(Invoker.java:218)
	at sun.nio.ch.AsynchronousChannelGroupImpl$1.run(AsynchronousChannelGroupImpl.java:112)
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.OutOfMemoryError: Metaspace
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:756)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at org.apache.catalina.loader.WebappClassLoaderBase.findClassInternal(WebappClassLoaderBase.java:2484)
	at org.apache.catalina.loader.WebappClassLoaderBase.findClass(WebappClassLoaderBase.java:876)
	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1379)
	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1223)
	at com.microsoft.sqlserver.jdbc.DTV.setValue(dtv.java:144)
	at com.microsoft.sqlserver.jdbc.Parameter.setValue(Parameter.java:369)
	at com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement.setValue(SQLServerPreparedStatement.java:1124)
	at com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement.setString(SQLServerPreparedStatement.java:1669)
	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.setString(HikariProxyPreparedStatement.java)
	at sun.reflect.GeneratedMethodAccessor133.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:67)
	at com.sun.proxy.$Proxy448.setString(Unknown Source)
	at org.apache.ibatis.type.StringTypeHandler.setNonNullParameter(StringTypeHandler.java:31)
	at org.apache.ibatis.type.StringTypeHandler.setNonNullParameter(StringTypeHandler.java:26)
	at org.apache.ibatis.type.BaseTypeHandler.setParameter(BaseTypeHandler.java:73)
	at org.apache.ibatis.type.UnknownTypeHandler.setNonNullParameter(UnknownTypeHandler.java:67)
	at org.apache.ibatis.type.BaseTypeHandler.setParameter(BaseTypeHandler.java:73)
	at com.baomidou.mybatisplus.core.MybatisParameterHandler.setParameters(MybatisParameterHandler.java:213)
	at org.apache.ibatis.executor.statement.PreparedStatementHandler.parameterize(PreparedStatementHandler.java:94)
	at org.apache.ibatis.executor.statement.RoutingStatementHandler.parameterize(RoutingStatementHandler.java:64)
	at sun.reflect.GeneratedMethodAccessor129.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)
	at com.sun.proxy.$Proxy446.parameterize(Unknown Source)
	at com.baomidou.mybatisplus.core.executor.MybatisSimpleExecutor.prepareStatement(MybatisSimpleExecutor.java:99)
	at com.baomidou.mybatisplus.core.executor.MybatisSimpleExecutor.doQuery(MybatisSimpleExecutor.java:68)
2022-08-19 12:04:30,190 [http-nio2-8080-exec-43] INFO  [16608818674214049235645] c.x.c.c.c.security.filter.AuthenticationFilter:172 - `/api/rest/xy-crm/external/inquire/sn/v1`  costTime:2768ms  response: {"errorCode":500,"userMessage":"服务器开小差了，请稍后重试！","developerMessage":"Handler dispatch failed; nested exception is java.lang.OutOfMemoryError: Metaspace","moreInfo":"org.springframework.web.util.NestedServletException: Handler dispatch failed; nested exception is java.lang.OutOfMemoryError: Metaspace"}
```



### 二、Metaspace

​		Metaspace 区域位于堆外，所以它的最大内存大小取决于系统内存，而不是堆大小，我们可以指定 MaxMetaspaceSize 参数来限定它的最大内存。

​		Metaspace 是用来存放 class metadata 的，class metadata 用于记录一个 Java 类在 JVM 中的信息，包括但不限于 JVM class file format 的运行时数据：

- Klass结构，可以理解为一个 Java 类在虚拟机内部的表示
- method metadata，包括方法的字节码、局部变量表、异常表、参数信息等
- 常量池
- 注解
- 方法计数器，记录方法被执行的次数，用来辅助 JIT 决策
- 其他

​		当一个类被加载时，它的类加载器会负责在 Metaspace 中分配空间用于存放这个类的元数据。

​		分配给一个类的空间，是归属于这个类的类加载器的，只有当这个类加载器卸载的时候，这个空间才会被释放。
​		所以，只有当这个类加载器加载的所有类都没有存活的对象，并且没有到达这些类和类加载器的引用时，相应的 Metaspace 空间才会被 GC 释放。

​		`java.lang.OutOfMemoryError: Metaspace`，由这个异常可知Metaspace空间增长是由于反射类加载、动态代理生成的类加载等导致的，也就是说Metaspace的大小和加载类的数据有关系，加载的类越多Metaspace占用的内存也就越大。



### 三、JVM参数

```shell
Java HotSpot(TM) 64-Bit Server VM (25.261-b12) for linux-amd64 JRE (1.8.0_261-b12), built on Jun 17 2020 23:41:40 by "java_re" with gcc 7.3.0

CommandLine flags: -XX:CompressedClassSpaceSize=260046848 -XX:GCLogFileSize=104857600 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/apache-tomcat-8.5.39/logs/xyCrmHeapDump.hprof -XX:InitialHeapSize=5704253440 -XX:MaxHeapSize=5704253440 -XX:MaxMetaspaceSize=268435456 -XX:MetaspaceSize=268435456 -XX:NumberOfGCLogFiles=5 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:+UseGCLogFileRotation


关键参数:
-Xmx5440M -Xms5440M -XX:MaxMetaspaceSize=256M -XX:MetaspaceSize=256M
-XX:+UseCompressedOops 允许对象指针压缩
-XX:+UseCompressedClassPointers 允许类指针压缩


优化参数:
-XX:MetaspaceSize=512M
-XX:MaxMetaspaceSize=512M
-XX:CompressedClassSpaceSize=410M
-XX:+UseCompressedClassPointers
```



### 四、GC日志

```
G1垃圾收集器工作流程:
1. 初始标记
2. 并发标记
3. 重新标记
4. 并发清除
```



```
2022-08-19T12:04:27.551+0800: 564416.369: [GC pause (Metadata GC Threshold) (young) (initial-mark)  // 初始标记，GC Roots是否可达
Desired survivor size 213909504 bytes, new threshold 15 (max 15)
- age   1:    6220088 bytes,    6220088 total
- age   2:    1635320 bytes,    7855408 total
- age   3:    1007960 bytes,    8863368 total
- age   4:     539712 bytes,    9403080 total
- age   5:      92720 bytes,    9495800 total
- age   6:     394568 bytes,    9890368 total
- age   7:     132232 bytes,   10022600 total
- age   8:     668800 bytes,   10691400 total
- age   9:     137536 bytes,   10828936 total
- age  10:     476888 bytes,   11305824 total
- age  11:      49168 bytes,   11354992 total
- age  12:       9656 bytes,   11364648 total
- age  13:      47992 bytes,   11412640 total
- age  14:     403264 bytes,   11815904 total
- age  15:     271016 bytes,   12086920 total
, 0.0222273 secs]
   [Parallel Time: 13.3 ms, GC Workers: 4]  // 并发执行的时间是: 13.3 ms, GC工作的线程数是: 4
      [GC Worker Start (ms): Min: 564416370.4, Avg: 564416370.4, Max: 564416370.5, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 6.2, Avg: 6.5, Max: 6.6, Diff: 0.4, Sum: 25.8] // 外部根分区扫描
      [Update RS (ms): Min: 0.9, Avg: 1.0, Max: 1.0, Diff: 0.1, Sum: 4.0] // 更新记忆集合 Update RSet，检测从年轻代指向老年代的对象
         [Processed Buffers: Min: 26, Avg: 30.8, Max: 43, Diff: 17, Sum: 123]
      [Scan RS (ms): Min: 0.2, Avg: 0.2, Max: 0.3, Diff: 0.1, Sum: 1.0] // 扫描RSet
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1] // 代码根扫描
      [Object Copy (ms): Min: 3.7, Avg: 3.9, Max: 4.1, Diff: 0.4, Sum: 15.7] // 转移和回收，拷贝存活的对象到survivor/old区域
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0] // 完成上述任务后，如果任务队列已空，则工作线程会发起终止要求
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1] // GC外部的并行活动，该部分并非GC的活动，而是JVM的活动导致占用了GC暂停时间（例如JNI编译）
      [GC Worker Total (ms): Min: 11.6, Avg: 11.7, Max: 11.7, Diff: 0.1, Sum: 46.7]
      [GC Worker End (ms): Min: 564416382.1, Avg: 564416382.1, Max: 564416382.1, Diff: 0.0]
   [Code Root Fixup: 0.0 ms] // 串行任务，根据转移对象更新代码根
   [Code Root Purge: 0.0 ms] // 串行任务，代码根清理
   [Clear CT: 0.4 ms] // 串行任务，清除全局卡片 Card Table 标记
   [Other: 8.6 ms]
      [Choose CSet: 0.0 ms] // 选择下次收集集合 CSet
      [Ref Proc: 7.0 ms] // 引用处理 Ref Proc，处理软引用、弱引用、虚引用、final引用、JNI引用
      [Ref Enq: 0.0 ms] // 引用排队 Ref Enq
      [Redirty Cards: 0.1 ms] // 卡片重新脏化 Redirty Cards：重新脏化卡片
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms] // 回收空闲巨型分区 Humongous Reclaim，通过查看所有根对象以及年轻代分区的RSet，如果确定RSet中巨型对象没有任何引用，该对象分区会被回收
      [Free CSet: 0.9 ms] // 释放分区 Free CSet
   [Eden: 2540.0M(3108.0M)->0.0B(3236.0M) Survivors: 156.0M->28.0M Heap: 3035.3M(5440.0M)->369.0M(5440.0M)]
 [Times: user=0.07 sys=0.01, real=0.02 secs] 

// 第一次full gc
2022-08-19T12:04:27.573+0800: 564416.391: [GC concurrent-root-region-scan-start] // 根分区扫描开始
2022-08-19T12:04:27.573+0800: 564416.391: [Full GC (Metadata GC Threshold) 2022-08-19T12:04:27.575+0800: 564416.392: [GC concurrent-root-region-scan-end, 0.0015334 secs] // 根分区扫描结束，耗时: 0.0015334 s
2022-08-19T12:04:27.575+0800: 564416.392: [GC concurrent-mark-start] // 并发标记阶段开始
 369M->205M(5440M), 0.6401213 secs]
   [Eden: 0.0B(3236.0M)->0.0B(3264.0M) Survivors: 28.0M->0.0B Heap: 369.0M(5440.0M)->205.9M(5440.0M)], [Metaspace: 210618K->209964K(1280000K)] // Metaspace已用205.04M，剩余50.96M未使用
 [Times: user=0.83 sys=0.00, real=0.64 secs] 
2022-08-19T12:04:28.214+0800: 564417.031: [Full GC (Last ditch collection)  205M->182M(5440M), 0.6147257 secs] // 经过Metadata GC Threshold触发的Full GC之后还是不能满足条件时触发，这次的Full GC会清理掉软引用
   [Eden: 0.0B(3264.0M)->0.0B(3264.0M) Survivors: 0.0B->0.0B Heap: 205.9M(5440.0M)->182.2M(5440.0M)], [Metaspace: 209964K->208952K(1280000K)] // Metaspace已用204.05M，剩余51.95M未使用
 [Times: user=0.79 sys=0.00, real=0.62 secs] 
2022-08-19T12:04:28.828+0800: 564417.646: [GC concurrent-mark-abort] // 并发标记阶段终止
2022-08-19T12:04:34.354+0800: 564423.172: [GC pause (Metadata GC Threshold) (young) (initial-mark)
Desired survivor size 213909504 bytes, new threshold 15 (max 15)
- age   1:    2399920 bytes,    2399920 total
- age   2:    5597088 bytes,    7997008 total
- age   3:    1630216 bytes,    9627224 total
- age   4:    1006864 bytes,   10634088 total
- age   5:     435496 bytes,   11069584 total
- age   6:      92560 bytes,   11162144 total
- age   7:     394504 bytes,   11556648 total
- age   8:     132200 bytes,   11688848 total
- age   9:     668768 bytes,   12357616 total
- age  10:     137536 bytes,   12495152 total
- age  11:     476888 bytes,   12972040 total
- age  12:      49072 bytes,   13021112 total
- age  13:       9544 bytes,   13030656 total
- age  14:      47888 bytes,   13078544 total
- age  15:     402968 bytes,   13481512 total
, 0.0104037 secs]
   [Parallel Time: 8.4 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 564423173.9, Avg: 564423173.9, Max: 564423174.0, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 5.8, Avg: 6.1, Max: 6.2, Diff: 0.4, Sum: 24.3]
      [Update RS (ms): Min: 0.4, Avg: 0.5, Max: 0.5, Diff: 0.1, Sum: 1.8]
         [Processed Buffers: Min: 1, Avg: 9.8, Max: 17, Diff: 16, Sum: 39]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.2, Avg: 0.4, Max: 0.6, Diff: 0.4, Sum: 1.5]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 6.9, Avg: 6.9, Max: 6.9, Diff: 0.0, Sum: 27.7]
      [GC Worker End (ms): Min: 564423180.9, Avg: 564423180.9, Max: 564423180.9, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.9 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 1.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 16.0M(3264.0M)->0.0B(3258.0M) Survivors: 0.0B->6144.0K Heap: 196.9M(5440.0M)->187.2M(5440.0M)]
 [Times: user=0.03 sys=0.00, real=0.02 secs] 
 
// 第二次full gc
2022-08-19T12:04:34.365+0800: 564423.183: [GC concurrent-root-region-scan-start]
2022-08-19T12:04:34.365+0800: 564423.183: [Full GC (Metadata GC Threshold) 2022-08-19T12:04:34.366+0800: 564423.183: [GC concurrent-root-region-scan-end, 0.0006646 secs]
2022-08-19T12:04:34.366+0800: 564423.183: [GC concurrent-mark-start]
 187M->172M(5440M), 0.5455488 secs]
   [Eden: 0.0B(3258.0M)->0.0B(3264.0M) Survivors: 6144.0K->0.0B Heap: 187.2M(5440.0M)->172.6M(5440.0M)], [Metaspace: 208973K->208973K(1280000K)]
 [Times: user=0.71 sys=0.00, real=0.54 secs] 
2022-08-19T12:04:34.911+0800: 564423.728: [Full GC (Last ditch collection)  172M->172M(5440M), 0.5319134 secs]
   [Eden: 0.0B(3264.0M)->0.0B(3264.0M) Survivors: 0.0B->0.0B Heap: 172.6M(5440.0M)->172.2M(5440.0M)], [Metaspace: 208973K->208956K(1280000K)]
 [Times: user=0.71 sys=0.00, real=0.53 secs] 
2022-08-19T12:04:35.443+0800: 564424.261: [GC concurrent-mark-abort]
2022-08-19T12:04:52.443+0800: 564441.260: [GC pause (Metadata GC Threshold) (young) (initial-mark)
Desired survivor size 213909504 bytes, new threshold 15 (max 15)
- age   1:     246752 bytes,     246752 total
, 0.0124701 secs]
   [Parallel Time: 8.6 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 564441262.2, Avg: 564441262.2, Max: 564441262.2, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 5.3, Avg: 5.5, Max: 5.7, Diff: 0.3, Sum: 22.0]
      [Update RS (ms): Min: 0.7, Avg: 0.8, Max: 0.9, Diff: 0.1, Sum: 3.1]
         [Processed Buffers: Min: 8, Avg: 16.5, Max: 22, Diff: 14, Sum: 66]
      [Scan RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.3]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Object Copy (ms): Min: 0.5, Avg: 0.7, Max: 0.8, Diff: 0.3, Sum: 2.6]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 7.0, Avg: 7.1, Max: 7.1, Diff: 0.0, Sum: 28.2]
      [GC Worker End (ms): Min: 564441269.2, Avg: 564441269.2, Max: 564441269.2, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 3.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 2.7 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.4 ms]
   [Eden: 936.0M(3264.0M)->0.0B(3258.0M) Survivors: 0.0B->6144.0K Heap: 1106.6M(5440.0M)->178.2M(5440.0M)]
 [Times: user=0.04 sys=0.00, real=0.02 secs] 
 
// 第三次full gc
2022-08-19T12:04:52.455+0800: 564441.273: [GC concurrent-root-region-scan-start]
2022-08-19T12:04:52.455+0800: 564441.273: [Full GC (Metadata GC Threshold) 2022-08-19T12:04:52.456+0800: 564441.274: [GC concurrent-root-region-scan-end, 0.0007574 secs]
2022-08-19T12:04:52.456+0800: 564441.274: [GC concurrent-mark-start]
 178M->173M(5440M), 0.5872637 secs]
   [Eden: 0.0B(3258.0M)->0.0B(3264.0M) Survivors: 6144.0K->0.0B Heap: 178.2M(5440.0M)->173.9M(5440.0M)], [Metaspace: 209173K->209153K(1280000K)]
 [Times: user=0.76 sys=0.00, real=0.58 secs] 
2022-08-19T12:04:53.043+0800: 564441.860: [Full GC (Last ditch collection)  173M->172M(5440M), 0.5759285 secs]
   [Eden: 0.0B(3264.0M)->0.0B(3264.0M) Survivors: 0.0B->0.0B Heap: 173.9M(5440.0M)->172.1M(5440.0M)], [Metaspace: 209153K->208967K(1280000K)]
 [Times: user=0.75 sys=0.00, real=0.58 secs] 
2022-08-19T12:04:53.619+0800: 564442.437: [GC concurrent-mark-abort]
2022-08-19T12:11:32.180+0800: 564840.998: [GC pause (G1 Evacuation Pause) (young)
Desired survivor size 213909504 bytes, new threshold 15 (max 15)
- age   1:     789856 bytes,     789856 total
, 0.0314445 secs]
   [Parallel Time: 21.1 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 564840997.9, Avg: 564840997.9, Max: 564840997.9, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 2.1, Avg: 2.7, Max: 4.1, Diff: 2.0, Sum: 10.8]
      [Update RS (ms): Min: 0.7, Avg: 1.8, Max: 2.3, Diff: 1.6, Sum: 7.4]
         [Processed Buffers: Min: 11, Avg: 38.8, Max: 53, Diff: 42, Sum: 155]
      [Scan RS (ms): Min: 0.2, Avg: 0.3, Max: 0.4, Diff: 0.2, Sum: 1.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.2]
      [Object Copy (ms): Min: 15.9, Avg: 16.1, Max: 16.3, Diff: 0.3, Sum: 64.3]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
         [Termination Attempts: Min: 44, Avg: 50.8, Max: 57, Diff: 13, Sum: 203]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 21.0, Avg: 21.0, Max: 21.1, Diff: 0.1, Sum: 84.2]
      [GC Worker End (ms): Min: 564841018.9, Avg: 564841019.0, Max: 564841019.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.5 ms]
   [Other: 9.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 8.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 1.1 ms]
   [Eden: 3264.0M(3264.0M)->0.0B(3204.0M) Survivors: 0.0B->60.0M Heap: 3439.7M(5440.0M)->230.8M(5440.0M)]
 [Times: user=0.09 sys=0.00, real=0.03 secs] 
```



![1](../../../images/java/oom/1.png)





### 五、Heap Dump文件分析

![image-20220820205536959](../../../images/java/oom/image-20220820205536959.png)

**MAT帮我们分析的可能存在内存泄漏的点**

- 由 org.apache.catalina.loader.ParallelWebappClassLoader 类加载器加载的实例 com.baomidou.mybatisplus.core.MybatisConfiguration 占用 33,245,776 (18.47%) 字节的内存（31.7MB），这些内存累积在HashMap中

- 类加载器/组件 org.apache.catalina.loader.ParallelWebappClassLoader 占用 32,004,744 (17.78%) 字节的内存（30.5MB）

![image-20220820210806797](../../../images/java/oom/image-20220820210806797.png)

![image-20220821174516489](../../../images/java/oom/image-20220821174516489.png)

- dominator tree 视图列出了堆中最大的对象，第二层级的节点表示当被第一层级的节点所引用到的对象，当第一层级对象被回收时，第二层级的对象也将被回收，可以帮助我们定位对象间的引用情况，以及垃圾回收时的引用依赖关系。支配树视图对数据进行了归类，体现了对象之间的依赖关系。我们通常会根据“深堆”进行倒序排序，可以很容易的看到占用内存比较高的几个对象，点击前面的箭头，即可一层层展开支配关系（依次找深堆明显比浅堆大的对象）
- 浅堆（Shallow Heap）: 代表了对象本身的内存占用，包括对象自身的内存占用，以及“为了引用”其他对象所占用的内存
- 深堆（Retained Heap）: 是一个统计结果，会循环计算引用的具体对象所占用的内存。但是深堆和“对象大小”有一点不同，深堆指的是一个对象被垃圾回收后，能够释放的内存大小，这些被释放的对象集合，叫做保留集（Retained Set）
- 我们分析问题时，往往分析那些深堆很大，尤其是那些浅堆比较小，但深堆比较大的对象，它们极有可能是有问题的对象



![image-20220821172532814](../../../images/java/oom/image-20220821172532814.png)

![image-20220821172454208](../../../images/java/oom/image-20220821172454208.png)

![image-20220821172620157](../../../images/java/oom/image-20220821172620157.png)

- sun.reflect.DelegatingClassLoader 有11664个，sun.reflect.GeneratedMethodAccessorXXXX 有 11335个，该类是反射用于加载生成的Method类时使用的类加载器

- 由上面GC日志分析出，Metaspace剩余51.95M（20.29%）未使用，那么此次Metaspace内存溢出了，使用率却只有79.71%，有两种可能：

  - 这次需要分配的内存达到了51.95（256M*20.29%）以上（不太可能）
  
  
    - 给类加载器分配的chunk使用率很低（从heap dump 提供的信息来看这种可能性更大，因为创建了很多类加载器，而每个类加载器又加载了很少的类）。见附3
  



![image-20220821174651840](../../../images/java/oom/image-20220821174651840.png)

![image-20220822200823285](../../../images/java/oom/image-20220822200823285.png)

![image-20220822201755164](../../../images/java/oom/image-20220822201755164.png)

![image-20220821220223433](../../../images/java/oom/image-20220821220223433.png)



- 查看某个sun.reflect.GeneratedMethodAccessorXXXX的GC Roots强引用，可见
  - Mybatis查询数据返回的字段值是通过反射设置到对象中
  - Jackson序列化
  - spring动态代理
  - 使用了Spring BeanUtils拷贝属性




### 六、为什么会有大量的sun.reflect.DelegatingClassLoader和sun.reflect.GeneratedMethodAccessorXXXX

DelegatingClassLoader是java反射中使用的类加载器，JVM对反射调用分两种情况：

* 默认使用native方法(JNI)进行反射操作，生成NativeMethodAccessorImpl类
* 一定条件下会生成字节码进行反射操作（默认15次JNI调用之后），即生成sun.reflect.GeneratedMethodAccessor<N>类，它是一个反射调用方法的包装类，代理不同的方法，类后缀序号递增

如果使用Java字节码生成的类，则需要拥有它自己的Java类和类加载器（sun/reflect/GeneratedMethodAccessorXXXX类和sun/reflect/DelegatingClassLoader)，这些类和类加载器使用本机内存。字节码存取器也可以被JIT编译，这样会增加本机内存的使用。如果Java反射被频繁使用，会显著地增加本机内存的使用。

Java虚拟机会首先使用JNI存取器，然后在访问了同一个类若干次后，会改为使用Java字节码存取器。这种当Java虚拟机从JNI存取器改为字节码存取器的行为被称为膨胀（Inflation）。Inflation机制提高了反射的性能，但是对于重度使用反射的项目可能存在隐患，它带来了两个问题：

- 初次加载的性能损失
- 动态加载的字节码类导致Metaspace持续增长

我们可以通过一个设置-Dsun.reflect.inflationThreshold=N控制这种行为，sun.reflect.inflationThreshold会告诉Java虚拟机使用JNI存取器多少次。如果设为0，即关闭了inflation，总是使用JNI存取器，不使用字节码存取器，进而可以回避Metaspace的过度使用。关掉Inflation会带来一定程度上的性能损失。



**由此得出的结论：**代码里大量使用了反射相关操作，那么JVM就会动态创建sun.reflect.GeneratedMethodAccessorXXXX类并由sun.reflect.DelegatingClassLoader类加载器加载



**源码分析：**

![image-20220822203555150](../../../images/java/oom/image-20220822203555150.png)

![image-20220822203956123](../../../images/java/oom/image-20220822203956123.png)



### 七、总结

- 将MetaspaceSize调整为512M，再观察GC日志，如果再次接近异常峰值，再尝试分析
- 兜底方案可以是设置 -Dsun.reflect.noInflation=true 或 -Dsun.reflect.inflationThreshold=0，禁用java反射的膨胀，通过牺牲反射性能，规避Metaspace OOM
- 项目中尽量不使用 Spring BeanUtils，这个也是通过反射去实现的（java.beans.PropertyDescriptor 里的get/set方法本身也是SoftReference包装的）
- 如果字段少的话直接赋值就行，多的话可以使用 Cglib 的 BeanCopier类，BeanCopier类底层是采用asm字节码操作方式来进行对象拷贝操作，性能损耗和内存开销都比较小，或者使用MapStruct



### 附: 参考资料

1. [大量类加载器创建导致诡异FullGC](https://heapdump.cn/article/1924890)

2. [从一起GC血案谈到反射原理](https://heapdump.cn/article/54786)

3. [深入理解堆外内存 Metaspace](https://heapdump.cn/article/1825490)

4. [一次Metaspace OutOfMemoryError问题排查记录](https://juejin.cn/post/7114516283290288158)

5. [JVM Metaspace内存溢出排查与总结](https://heapdump.cn/article/2152817)

6. [大量DelegatingClassLoader类加载器，导致Perm区溢出](https://blog.csdn.net/hellozhxy/article/details/80559419)

7. [JVM源码分析之Metaspace解密](https://heapdump.cn/article/210111)