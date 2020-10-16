package com.zync.boot.redistools.aspect;

import com.google.common.collect.Lists;
import com.zync.boot.redistools.annotation.DistributedLimit;
import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLimitKeyType;
import com.zync.boot.redistools.common.DistributedLimitType;
import com.zync.boot.redistools.exception.RedisToolsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 分布式限流切面
 * @date 2020/9/20 15:46
 */
@Slf4j
@Aspect
public class DistributedLimitAspect {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 切点
     */
    @Pointcut(value = "@annotation(com.zync.boot.redistools.annotation.DistributedLimit)")
    public void pointcut() {
    }

    /**
     * 环绕通知
     * @param point ProceedingJoinPoint
     * @param limit {@link DistributedLimit}
     * @return
     */
    @Around(value = "pointcut() && @annotation(limit)", argNames = "point, limit")
    public Object around(ProceedingJoinPoint point, DistributedLimit limit) {
        // 功能名称
        String name = limit.name();
        // 给定的时间范围（单位：秒）
        long period = limit.period();
        // 给定时间范围内可以访问的次数
        long count = limit.count();
        // 限流key的类型
        DistributedLimitKeyType keyType = limit.keyType();
        // 限流类型
        DistributedLimitType limitType = limit.limitType();

        // 根据不同的限流key的类型来获取不同的限流key
        String key;
        switch (keyType) {
            case CUSTOMER:
                key = limit.key();
                break;
            case IP:
                key = getIpAddress();
                break;
            default:
                key = StringUtils.EMPTY;
        }
        log.info("limiting's name is {} key is {} period is {} count is {}", name, key, period, count);

        try {
            Boolean bool = redisTemplate.execute(redisScript(limitType), Lists.newArrayList(key), count, period);
            if (Boolean.TRUE.equals(bool)) {
                return point.proceed();
            }
            throw new RedisToolsServiceException("Your operation is too frequent and has been blacklisted, please wait two seconds and try again.");
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RedisToolsServiceException(e.getMessage(), e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 RedisScript
     * @param limitType 限流类型
     * @return RedisScript
     */
    private RedisScript<Boolean> redisScript(DistributedLimitType limitType) {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource(limitType.getPath()));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    /**
     * 获取客户端ip地址
     * @return ip
     */
    private String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(Const.UNKNOWN, ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(Const.UNKNOWN, ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(Const.UNKNOWN, ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
