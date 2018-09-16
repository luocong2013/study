package com.ccyw.springboot.own.common.service.impl;

import com.ccyw.springboot.own.common.bean.Learn;
import com.ccyw.springboot.own.common.service.LearnService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务层测试
 * @date 2018/8/19 18:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnServiceImplTest {

    @Autowired
    private LearnService learnService;

    @Test
    public void selectAll() {
        List<Learn> learns = learnService.selectAll();
        learns.forEach(item -> System.out.println(item.getAge()));
    }

    @Test
    public void selectOne() {
        Learn learn = new Learn();
        learn.setId(1);
        learn.setCupsize("C");
        Learn l = learnService.selectOne(learn);
        System.out.println(l.getAge());
    }
}