package com.ccyw.springboot.own.common.service;

import com.ccyw.springboot.own.common.bean.Learn;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层
 * @date 2018/8/19 18:09
 */
public interface LearnService {

    /**
     * 查询所有数据
     * @return
     */
    List<Learn> selectAll();

    /**
     * 查询一条数据
     * @param learn
     * @return
     */
    Learn selectOne(Learn learn);

    /**
     * 保存
     * @param learn
     */
    void save(Learn learn);

    /**
     * 批量保存
     * @param learns
     */
    void batchSave(List<Learn> learns);

}
