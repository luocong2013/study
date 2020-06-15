package com.zync.example.springbootdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:03
 */
@Aspect
@Component
public class Log2Aspect {

    @Pointcut(value = "@annotation(com.zync.example.springbootdemo.aspect.LogOper)")
    public void pointcut2() {
    }

    @Before(value = "pointcut2() && @annotation(logOper)", argNames = "joinPoint, logOper")
    public void doBefore(JoinPoint joinPoint, LogOper logOper) {
        System.out.println(logOper.message());
    }

    @Around(value = "pointcut2() && @annotation(logOper)", argNames = "pjd, logOper")
    public Object aroundMethod(ProceedingJoinPoint pjd, LogOper logOper) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            System.out.println(logOper.message());
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
    }

}
