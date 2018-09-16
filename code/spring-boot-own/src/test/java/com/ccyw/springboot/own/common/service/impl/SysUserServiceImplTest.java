package com.ccyw.springboot.own.common.service.impl;

import com.ccyw.springboot.own.common.bean.SysUser;
import com.ccyw.springboot.own.common.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层接口测试
 * @date 2018/8/19 21:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceImplTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void selectAll() {
        List<SysUser> sysUsers = sysUserService.selectAll();
        sysUsers.forEach(item -> System.out.println(item.getSysUserRealName()));
    }

    @Test
    public void selectByPrimaryKey() {
        SysUser sysUser = sysUserService.selectByPrimaryKey(22L);
        System.out.println(sysUser.getSysUserLoginName());
    }

    @Test
    public void save() {
    }

    @Test
    public void batchSave() {
    }
}