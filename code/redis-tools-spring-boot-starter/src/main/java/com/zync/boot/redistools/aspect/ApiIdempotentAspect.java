package com.zync.boot.redistools.aspect;

import com.zync.boot.redistools.annotation.ApiIdempotent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 接口幂等性切面
 *
 * @author luocong
 * @version v1.0.2
 * @date 2023/2/6 12:53
 */
@Slf4j
@Aspect
public class ApiIdempotentAspect {

    /**
     * 切点
     */
    @Pointcut(value = "@annotation(com.zync.boot.redistools.annotation.ApiIdempotent)")
    public void pointcut() {
    }

    @Before(value = "pointcut() && @annotation(idempotent)", argNames = "point, idempotent")
    public void before(JoinPoint point, ApiIdempotent idempotent) {

    }


}
