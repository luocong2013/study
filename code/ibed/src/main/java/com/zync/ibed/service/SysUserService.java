package com.zync.ibed.service;

import com.zync.ibed.bean.po.SysUser;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户Service
 * @date 2019/6/30 22:22
 */
public interface SysUserService {

    /**
     * 保存或更新用户
     * @param user 用户对象
     */
    int saveOrUpdate(SysUser user);

}
