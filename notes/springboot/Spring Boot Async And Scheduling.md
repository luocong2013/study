## Spring Boot Async And Scheduling

## 一、Async

1、启动Async

> 启动类加注解 @EnableAsync
>
> 这个注解会 @Import(AsyncConfigurationSelector.class)
>
> 创建 AsyncAnnotationBeanPostProcessor
>
> 在 AsyncAnnotationBeanPostProcessor 的 setBeanFactory 里 创建 AsyncAnnotationAdvisor
>
> 在 AsyncAnnotationAdvisor 的 构造器里 创建切面 AnnotationAsyncExecutionInterceptor
>
> 和创建切点 只针对 @Async 注解



2、执行@Async异步任务的过程

> 通过拦截器进入 AsyncExecutionInterceptor 的 invoke 方法执行异步任务
>
> 怎么进入这个方法的？（以JdkDynamic为例)
>
> JdkDynamicAopProxy 调用 invoke 
>
> List `<Object>` chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
>
> MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
>
> retVal = invocation.proceed();
>
> return dm.interceptor.invoke(this);


3、执行Async任务的线程池

> 扩展方式一：
>
> 1、实现 AsyncConfigurer 接口，并重写 getAsyncExecutor 方法
>
> 这种方式拿到的线程池是执行Async任务默认的线程池，如果要改变线程池需要使用 @Async("otherTaskExecutor")
>
> 扩展方式二：
>
> 创建 TaskExecutor 的 Bean，注意需要 指定 @Primary，否则容器里存在多个 TaskExecutor 的 Bean 的时候
>
> 那得到的将是 SimpleAsyncTaskExecutor
>
> 见源码：
>
> org.springframework.aop.interceptor.AsyncExecutionInterceptor#getDefaultExecutor
>
> org.springframework.aop.interceptor.AsyncExecutionAspectSupport#getDefaultExecutor
>
> 扩展方式三：
>
> 使用Spring Boot 自动配置
>
> org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration#applicationTaskExecutor
>
> 在application.yml配置文件里配置相应参数即可



## 二、Scheduling

1、启动Scheduling

> 启动类加注解 @EnableScheduling
>
> @Import(SchedulingConfiguration.class)
>
> 创建 ScheduledAnnotationBeanPostProcessor
>
> org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor#resolveSchedulerBean
>
> 在spring容器中找 taskScheduler 的 bean 
>
> 这个 bean 默认在 org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration 创建
>
> 类似的 可以通过 在 application.yml 配置文件中配置相应参数即可
