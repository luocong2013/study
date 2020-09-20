package com.zync.boot.redistools.aspect;

import com.zync.boot.redistools.annotation.DistributedLimit;
import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLimitType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author luoc
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
        // 限流类型
        DistributedLimitType limitType = limit.limitType();

        /**
         * 根据不同的限流类型来获取不同的限流key
         */
        String key;
        switch (limitType) {
            case CUSTOMER:
                key = limit.key();
                break;
            case IP:
                key = getIpAddress();
                break;
            default:
                key = StringUtils.EMPTY;
        }

        return null;
    }

    /**
     * 获取客户端ip地址
     *
     * @return
     */
    public String getIpAddress() {
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
