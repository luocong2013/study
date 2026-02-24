package com.mountain.web.basic.service;

import java.util.List;

/**
 * 鉴权服务接口
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 14:53
 **/
public interface PermissionService {

    /**
     * 获取请求所需要的权限标识
     *
     * @param url         请求url
     * @param requestType 请求类型 POST、GET...
     * @return 访问该url的权限标识列表
     */
    List<String> listAuthorities(String url, String requestType);
}
