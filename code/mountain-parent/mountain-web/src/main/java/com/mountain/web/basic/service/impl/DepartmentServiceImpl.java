package com.mountain.web.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.web.basic.mapper.DepartmentMapper;
import com.mountain.web.basic.pojo.po.Department;
import com.mountain.web.basic.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * 针对表【basic_department(基础设置-部门)】的数据库操作Service实现
 *
 * @author luocong
 * @version v1.0
 * @since 2026/2/10 15:30
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}




