package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.mapper.UserRoleMapper;
import com.mountain.web.basic.pojo.po.UserRole;
import com.mountain.web.basic.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_user_role(基础设置-用户角色关系)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}




