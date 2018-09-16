package com.ccyw.springboot.own.jdbc.dao.impl;

import com.ccyw.springboot.own.common.bean.Learn;
import com.ccyw.springboot.own.jdbc.dao.LearnDaoJdbc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description Learn Dao层接口实现
 * @date 2018/8/14 22:39
 */
@Slf4j
@Repository
public class LearnDaoJdbcImpl implements LearnDaoJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Learn learn) {
        String sql = "INSERT INTO learn (cupsize, age, money) VALUES (?,?,?)";
        try {
            jdbcTemplate.update(sql, learn.getCupsize(), learn.getAge(), learn.getMoney());
        } catch (DataAccessException e) {
            log.error("执行sql【{}】，插入数据失败", sql);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Learn learn) {
        String sql = "UPDATE learn SET age = ?, money = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, learn.getAge(), learn.getMoney(), learn.getId());
        } catch (DataAccessException e) {
            log.error("执行sql【{}】，更新失败", sql);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Learn> getLearns() {
        String sql = "SELECT id, cupsize, age, money FROM learn";
        List<Learn> learns = null;
        try {
            learns = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Learn.class));
        } catch (DataAccessException e) {
            log.error("执行sql【{}】，查询所有数据失败", sql);
            e.printStackTrace();
        }
        return learns;
    }
}
