# Spring Security 启动流程

## 一、总览

SpringBoot自动配置两个SpringSecurity的核心配置类：

SecurityAutoConfiguration
SecurityFilterAutoConfiguration



## 二、SecurityAutoConfiguration

1、SecurityAutoConfiguration 导入 SpringBootWebSecurityConfiguration 配置文件

2、SpringBootWebSecurityConfiguration 配置默认的 SecurityFilterChain（如果没有自定义就会使用）、配置                      ErrorPageSecurityFilter、启动 @EnableWebSecurity

3、@EnableWebSecurity 导入 WebSecurityConfiguration、HttpSecurityConfiguration两个配置文件 及 启动                        @EnableGlobalAuthentication

4、@EnableGlobalAuthentication 导入 AuthenticationConfiguration配置文件（配置全局的AuthenticationManager）

5、WebSecurityConfiguration 创建 springSecurityFilterChain Bean
  ① 获取所有实现了 WebSecurityConfigurerAdapter 的实现类（已过时）
  ② 注入所有 SecurityFilterChain Bean
  ③ WebSecurityConfigurerAdapter 方式和 SecurityFilterChain 方式只能二选一
  ④ WebSecurity build
  ⑤ WebSecurityConfigurerAdapter 方式会在 WebSecurity build 的 init 方法里创建 HttpSecurity，再调用 WebSecurity 的              addSecurityFilterChainBuilder 方法添加构建器
  ⑥ 注入 SecurityFilterChain Bean 方式是注入后直接调用 WebSecurity 的 addSecurityFilterChainBuilder 方法添加构建器，逻辑没       那么绕（这也许是推荐使用的原因）
  ⑦ 可以实现 WebSecurityCustomizer 接口自定义 WebSecurity build 的一些参数
  ⑧ 在 WebSecurity 的 performBuild 方法里构建 FilterChainProxy，即得到 springSecurityFilterChain Bean


6、HttpSecurityConfiguration 创建默认的 HttpSecurity （注意：Scope 是 prototype，因为每个 SecurityFilterChain 的 HttpSecurity      可能是不同的）





## 3、SecurityFilterAutoConfiguration

1、SecurityFilterAutoConfiguration 创建 DelegatingFilterProxyRegistrationBean，主要是为了将 DelegatingFilterProxy 注册    到 Servlet 的 Filter 过滤链中

2、DelegatingFilterProxy 代理 springSecurityFilterChain Bean （实际就是FilterChainProxy）并将自己注册到 Servlet 的       Filter 过滤链中



## 4、SpringBoot 编程式注入Servlet Filter 原理

实现Filter接口，再配置Bean，如：

```java
public class ErrorPageSecurityFilter implements Filter {}

@Bean
FilterRegistrationBean<ErrorPageSecurityFilter> errorPageSecurityFilter(ApplicationContext context) {
  FilterRegistrationBean<ErrorPageSecurityFilter> registration = new FilterRegistrationBean<>(
    new ErrorPageSecurityFilter(context));
  registration.setDispatcherTypes(DispatcherType.ERROR);
  return registration;
}

@Bean
@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(
  SecurityProperties securityProperties) {
  DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
    DEFAULT_FILTER_NAME);
  registration.setOrder(securityProperties.getFilter().getOrder());
  registration.setDispatcherTypes(getDispatcherTypes(securityProperties));
  return registration;
}
```



## 5、为什么有些Filter不用使用 FilterRegistrationBean 包装，只需使用 @Component 注解将其加入Spring容器中，Filter功能就能使用了

原来是Spring Boot源码里对加入Spring容器中的 Filter.class 做了适配处理，见源码

org.springframework.boot.web.servlet.ServletContextInitializerBeans#addAdaptableBeans

Spring Boot 针对没有实现 ServletRegistrationBean、FilterRegistrationBean、DelegatingFilterProxyRegistrationBean、ServletListenerRegistrationBean 类的 Servlet、Filter 、EventListener 做了适配，会为其创建 ServletRegistrationBean、FilterRegistrationBean、ServletListenerRegistrationBean

```java
@SuppressWarnings("unchecked")
protected void addAdaptableBeans(ListableBeanFactory beanFactory) {
  MultipartConfigElement multipartConfig = getMultipartConfig(beanFactory);
  addAsRegistrationBean(beanFactory, Servlet.class, new ServletRegistrationBeanAdapter(multipartConfig));
  addAsRegistrationBean(beanFactory, Filter.class, new FilterRegistrationBeanAdapter());
  for (Class<?> listenerType : ServletListenerRegistrationBean.getSupportedTypes()) {
    addAsRegistrationBean(beanFactory, EventListener.class, (Class<EventListener>) listenerType,
                          new ServletListenerRegistrationBeanAdapter());
  }
}
```

