package com.zync.security.springsecurity.dao;

import com.zync.security.springsecurity.model.PermissionDTO;
import com.zync.security.springsecurity.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户DAO
 * @date 2020/7/19 14:48
 */
@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据账号查询用户
     * @param username 账号
     * @return
     */
    public UserDTO getUserByUsername(String username) {
        String sql = "select id, username, password, fullname, mobile from t_user where username = ?";
        List<UserDTO> users = jdbcTemplate.query(sql, new Object[]{ username }, new BeanPropertyRowMapper<>(UserDTO.class));
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    /**
     * 根据用户ID查询用户权限
     * @param userId
     * @return
     */
    public List<String> getPermissionsByUserId(Long userId) {
        String sql = "SELECT * FROM t_permission WHERE id IN (\n" +
                " SELECT permission_id FROM t_role_permission WHERE role_id IN (\n" +
                "   SELECT role_id FROM t_user_role WHERE user_id = ?\n" +
                " )\n" +
                ")";
        List<PermissionDTO> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDTO.class));
        return list.stream().map(PermissionDTO::getCode).collect(Collectors.toList());
    }
}
