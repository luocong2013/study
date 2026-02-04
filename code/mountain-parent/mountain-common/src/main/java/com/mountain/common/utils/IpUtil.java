package com.mountain.common.utils;

import com.mountain.common.base.Const;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * IP工具类
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/1 11:42
 */
@UtilityClass
public class IpUtil {

    /**
     * 获取客户机ip地址
     *
     * @param request HttpServletRequest
     * @return java.lang.String
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Original-Forwarded-For");
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || Strings.CI.equals(Const.UNKNOWN, ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (Strings.CS.contains(ip, Const.COMMA)) {
            ip = StringUtils.substring(ip, Strings.CS.lastIndexOf(ip, Const.COMMA) + 1, ip.length());
        }
        return ip;
    }
}
