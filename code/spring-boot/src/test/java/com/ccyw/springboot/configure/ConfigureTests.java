package com.ccyw.springboot.configure;

import com.ccyw.springboot.dao.BookDao;
import com.ccyw.springboot.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/4 22:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigureTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookDao bookDao2;

    @Test
    public void say() {
        userDao.say();
    }

    @Test
    public void getBook() {
        bookDao.getBook();
    }
}
