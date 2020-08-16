package com.zync.distributed.security.uaa.model;

import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 权限模型
 * @date 2020/7/19 16:41
 */
@Data
public class PermissionDTO {
    /**
     * 权限ID
     */
    private Long id;
    /**
     * 权限code
     */
    private String code;
    /**
     * 权限描述
     */
    private String description;
    /**
     * 权限对应的URL
     */
    private String url;
}
