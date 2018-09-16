package com.ccyw.springboot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/17 22:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTests {

    @Autowired
    private BookDao bookDao;

    @Test
    public void getBook() {
        bookDao.getBook();
    }

}
