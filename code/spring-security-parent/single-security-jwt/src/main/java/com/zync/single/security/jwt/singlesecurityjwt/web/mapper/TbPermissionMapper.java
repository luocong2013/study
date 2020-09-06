package com.zync.single.security.jwt.singlesecurityjwt.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zync.single.security.jwt.singlesecurityjwt.web.model.TbPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户权限Mapper
 * @date 2020/8/30 16:53
 */
@Mapper
public interface TbPermissionMapper extends BaseMapper<TbPermission> {

    /**
     * 通过用户ID查询用户权限
     * @param id
     * @return
     */
    @Select("SELECT ename FROM tb_permission WHERE id IN (SELECT permission_id FROM tb_role_permission WHERE role_id IN (SELECT role_id FROM tb_user_role WHERE user_id = #{id}))")
    List<String> selectByUserId(@Param("id") Long id);

}