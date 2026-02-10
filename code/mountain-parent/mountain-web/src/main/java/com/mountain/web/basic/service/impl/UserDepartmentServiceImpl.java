package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.mapper.UserDepartmentMapper;
import com.mountain.web.basic.pojo.po.UserDepartment;
import com.mountain.web.basic.service.UserDepartmentService;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_user_department(基础设置-用户部门关系)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements UserDepartmentService {

}




