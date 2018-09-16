package com.ccyw.springboot.own.jdbc.service;

import com.ccyw.springboot.own.common.bean.Learn;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description Learn Service层
 * @date 2018/8/14 22:40
 */
public interface LearnServiceJdbc {

    /**
     * 保存
     * @param learn
     * @return
     */
    boolean save(Learn learn);

    /**
     * 更新
     * @param learn
     * @return
     */
    boolean update(Learn learn);

    /**
     * 获取所有数据
     * @return
     */
    List<Learn> getLearns();

    /**
     * 更新两条数据
     * @param learn1
     * @param learn2
     * @return
     */
    boolean updateTwo(Learn learn1, Learn learn2);
}
