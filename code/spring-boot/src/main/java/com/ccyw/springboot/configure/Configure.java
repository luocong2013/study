package com.ccyw.springboot.configure;

import com.ccyw.springboot.dao.BookDao;
import com.ccyw.springboot.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/4 22:18
 */
@Configuration
//@Import({UserDao.class})
public class Configure {

    @Bean(name = "bookDao", initMethod = "init", destroyMethod = "destroy")
    @Scope("singleton")
    public BookDao bookDao() {
        return new BookDao();
    }

    @Bean(name = "userDao", initMethod = "init", destroyMethod = "destroy")
    @Scope(value = "singleton")
    public UserDao userDao() {
        return new UserDao();
    }

}
