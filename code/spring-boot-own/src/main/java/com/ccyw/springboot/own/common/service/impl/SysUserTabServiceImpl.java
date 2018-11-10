package com.ccyw.springboot.own.common.service.impl;

import com.ccyw.springboot.own.common.bean.SysUserTab;
import com.ccyw.springboot.own.common.mapper.SysUserTabMapper;
import com.ccyw.springboot.own.common.service.SysUserTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 系统用户表服务层接口实现
 * @date 2018/11/10 21:28
 */
@Service
public class SysUserTabServiceImpl implements SysUserTabService {

    @Autowired
    private SysUserTabMapper sysUserTabMapper;

    @Override
    public List<SysUserTab> selectAll() {
        return sysUserTabMapper.selectAll();
    }

    @Override
    public SysUserTab selectOne(SysUserTab record) {
        return sysUserTabMapper.selectOne(record);
    }

    @Override
    public void save(SysUserTab record) {
        sysUserTabMapper.insertSelective(record);
    }

    @Override
    public void beachSave(List<SysUserTab> records) {
        sysUserTabMapper.insertList(records);
    }
}
