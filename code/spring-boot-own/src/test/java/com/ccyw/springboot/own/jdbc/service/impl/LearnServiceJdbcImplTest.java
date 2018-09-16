package com.ccyw.springboot.own.jdbc.service.impl;

import com.ccyw.springboot.own.common.bean.Learn;
import com.ccyw.springboot.own.jdbc.service.LearnServiceJdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luoc
 * @version V1.0.0
 * @description LearnServiceJdbc 测试
 * @date 2018/8/14 22:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnServiceJdbcImplTest {

    @Autowired
    private LearnServiceJdbc learnService;

    @Test
    public void updateTwo() {
        Learn learn1 = new Learn(1, "C", 15, 150.0);
        Learn learn2 = new Learn(5, "C", 20, 200.0);
        learnService.updateTwo(learn1, learn2);
        System.out.println("执行完毕");
    }
}