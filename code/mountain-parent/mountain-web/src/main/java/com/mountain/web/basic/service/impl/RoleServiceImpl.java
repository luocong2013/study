package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.pojo.po.Role;
import com.mountain.web.basic.service.RoleService;
import com.mountain.web.basic.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_role(基础设置-角色)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}




