package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.mapper.UserMapper;
import com.mountain.web.basic.pojo.po.User;
import com.mountain.web.basic.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_user(基础设置-用户)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




