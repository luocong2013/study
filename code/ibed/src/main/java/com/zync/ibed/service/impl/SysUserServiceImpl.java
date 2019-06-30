package com.zync.ibed.service.impl;

import com.zync.ibed.mapper.SysUserMapper;
import com.zync.ibed.po.SysUser;
import com.zync.ibed.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户Service实现
 * @date 2019/6/30 22:23
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int saveOrUpdate(SysUser user) {
        Objects.requireNonNull(user, "user must not be null");
        if (user.getSysUserId() != null) {
            return sysUserMapper.updateByPrimaryKeySelective(user);
        }
        return sysUserMapper.insertSelective(user);
    }
}
