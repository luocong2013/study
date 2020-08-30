package com.zync.single.security.jdbc.singlesecurityjdbc.web.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_permission
 * @author 
 */
@Data
public class TbPermission implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long parentId;

    /**
     * 权限名字
     */
    private String name;

    /**
     * 权限名字
     */
    private String ename;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}