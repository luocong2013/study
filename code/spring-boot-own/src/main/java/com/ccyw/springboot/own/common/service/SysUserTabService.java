package com.ccyw.springboot.own.common.service;

import com.ccyw.springboot.own.common.bean.SysUserTab;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 系统用户表服务层接口
 * @date 2018/11/10 21:27
 */
public interface SysUserTabService {

    /**
     * 查询所有系统用户
     * @return
     */
    List<SysUserTab> selectAll();

    /**
     * 查询用户
     * @param record
     * @return
     */
    SysUserTab selectOne(SysUserTab record);

    /**
     * 保存用户
     * @param record
     */
    void save(SysUserTab record);

    /**
     * 批量保存用户
     * @param records
     */
    void beachSave(List<SysUserTab> records);
}
