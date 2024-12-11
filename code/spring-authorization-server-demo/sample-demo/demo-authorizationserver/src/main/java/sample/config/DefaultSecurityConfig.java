/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import sample.federation.FederatedIdentityAuthenticationSuccessHandler;

/**
 * @author Joe Grandja
 * @author Steve Riesenberg
 * @since 1.1
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

	// 过滤器链
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> // ① 配置鉴权的
						authorize
								.requestMatchers("/assets/**", "/webjars/**", "/login", "/oauth2/**").permitAll() // ② 忽略鉴权的url
								.anyRequest().authenticated() // ③ 排除忽略的其他url就需要鉴权了
				)
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(formLogin ->
						// 说明：formLogin 和 oauth2Login 都可以重定向到 GET /login 页面，但 formLogin 主要还有处理自定义登录页面登录请求的作用
						// 1、设置自定义登录页面（需和 sample.web.LoginController#login 地址一致 GET /login）
						//    鉴权失败后，重定向到 GET /login {@link org.springframework.security.web.access.ExceptionTranslationFilter#sendStartAuthentication}
						//    this.authenticationEntryPoint.commence(request, response, reason);
						//    这里是通过 {@link org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer#registerAuthenticationEntryPoint} 方法
						//    给 ExceptionTranslationFilter 设置 AuthenticationEntryPoint

						// 2、设置登录处理地址为 POST /login（需和自定义登录页面 login.html 中登录处理地址一致）
						// 3、this.customLoginPage = true; 目的是禁用默认登录页面 {@link org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer#initDefaultLoginFilter}
						// 4、其他
						formLogin.loginPage("/login") // ④ 授权服务认证页面（可以配置相对和绝对地址，前后端分离的情况下填前端的url）
				)
				.oauth2Login(oauth2Login ->
						// 说明：formLogin 和 oauth2Login 都可以重定向到 GET /login 页面，但 formLogin 主要还有处理自定义登录页面登录请求的作用
						// 1、设置自定义登录页面（需和 sample.web.LoginController#login 地址一致 GET /login）
						//    鉴权失败后，重定向到 GET /login {@link org.springframework.security.web.access.ExceptionTranslationFilter#sendStartAuthentication}
						//    this.authenticationEntryPoint.commence(request, response, reason);
						//    这里是通过 {@link org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer#registerAuthenticationEntryPoint} 方法
						//    给 ExceptionTranslationFilter 设置 AuthenticationEntryPoint

						// 2、登录处理地址默认是：/login/oauth2/code/*
						// 3、this.customLoginPage = true; 目的是禁用默认登录页面 {@link org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer#initDefaultLoginFilter}
						// 4、其他
						oauth2Login.loginPage("/login") // ⑤ oauth2的认证页面（也可配置绝对地址）
								.successHandler(authenticationSuccessHandler()) // ⑥ 登录成功后的处理
				);

		return http.build();
	}


	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new FederatedIdentityAuthenticationSuccessHandler();
	}

	// 初始化了一个用户在内存里面（这样就不会每次启动就再去生成密码了）
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user1")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}


	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	/**
	 * Web security customizer web security customizer.
	 *
	 * @return the web security customizer
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/h2-console/**");
	}

	/**
     * 跨域过滤器配置
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(configurationSource);
    }

}
