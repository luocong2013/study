package com.ccyw.springboot.own.common.service.impl;

import com.ccyw.springboot.own.common.bean.SysUser;
import com.ccyw.springboot.own.common.mapper.SysUserMapper;
import com.ccyw.springboot.own.common.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层接口实现
 * @date 2018/8/19 21:32
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectAll();
    }

    @Override
    public SysUser selectByPrimaryKey(Long sysUserId) {
        return sysUserMapper.selectByPrimaryKey(sysUserId);
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public void batchSave(List<SysUser> sysUsers) {
        sysUserMapper.insertList(sysUsers);
    }
}
