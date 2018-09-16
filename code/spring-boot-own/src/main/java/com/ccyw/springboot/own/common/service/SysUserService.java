package com.ccyw.springboot.own.common.service;

import com.ccyw.springboot.own.common.bean.SysUser;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层接口
 * @date 2018/8/19 18:28
 */
public interface SysUserService {

    /**
     * 查询所有数据
     * @return
     */
    List<SysUser> selectAll();

    /**
     * 根据id查询数据
     * @param sysUserId
     * @return
     */
    SysUser selectByPrimaryKey(Long sysUserId);

    /**
     * 保存数据
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 批量保存数据
     * @param sysUsers
     */
    void batchSave(List<SysUser> sysUsers);
}
