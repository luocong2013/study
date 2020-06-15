package com.zync.example.springbootdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author luocong
 * @description 日志切面
 * @date 2020/5/25 11:20
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut(value = "execution(public * com.zync.example.springbootdemo.controller.LogController.*(..))")
    public void pointcut(){
    }

    @Before("pointcut()")
    public void mybefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        LOGGER.info("url={}", request.getRequestURL());
        //method
        LOGGER.info("method={}", request.getMethod());
        //ip
        LOGGER.info("ip={}", request.getRemoteAddr());
        //类方法
        LOGGER.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        LOGGER.info("args={}", joinPoint.getArgs());

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        System.out.println("The method " + methodName + " begins with " + Arrays.asList(args));
    }

    @After("pointcut()")
    public void myafter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " ends");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void myafterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " ends with " + result);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void myafterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " occurs excetion:" + e);
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数.
     * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
    /*@Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            //执行目标方法
            result = pjd.proceed();
            //返回通知
            System.out.println("The method " + methodName + " ends with " + result);
        } catch (Throwable e) {
            //异常通知
            System.out.println("The method " + methodName + " occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends");
        return result;
    }*/
}
