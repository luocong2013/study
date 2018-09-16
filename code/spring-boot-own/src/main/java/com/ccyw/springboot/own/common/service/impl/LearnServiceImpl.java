package com.ccyw.springboot.own.common.service.impl;

import com.ccyw.springboot.own.common.bean.Learn;
import com.ccyw.springboot.own.common.mapper.LearnMapper;
import com.ccyw.springboot.own.common.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层接口实现
 * @date 2018/8/19 18:10
 */
@Service
public class LearnServiceImpl implements LearnService {

    @Autowired
    private LearnMapper learnMapper;

    @Override
    public List<Learn> selectAll() {
        return learnMapper.selectAll();
    }

    @Override
    public Learn selectOne(Learn learn) {
        return learnMapper.selectOne(learn);
    }

    @Override
    public void save(Learn learn) {
        learnMapper.insertSelective(learn);
    }

    @Override
    public void batchSave(List<Learn> learns) {
        learnMapper.insertList(learns);
    }
}
