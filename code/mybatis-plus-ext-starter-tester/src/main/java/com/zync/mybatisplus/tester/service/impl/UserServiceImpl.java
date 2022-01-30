package com.zync.mybatisplus.tester.service.impl;

import com.zync.boot.mybatisplus.ext.service.BasicServiceImpl;
import com.zync.mybatisplus.tester.entity.User;
import com.zync.mybatisplus.tester.mapper.UserMapper;
import com.zync.mybatisplus.tester.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务接口实现类
 * </p>
 *
 * @author luocong
 * @since 2022-01-30
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements UserService {

}
