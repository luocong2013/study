package com.ccyw.springboot.own;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author luoc
 * @date 2018/8/14 22:35
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ccyw.springboot.own.common.mapper")
@ServletComponentScan("com.ccyw.springboot.own.common.filter")
public class SpringBootOwnApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBootOwnApplication.class, args);
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println("dataSource is : " + dataSource);
		// 检查数据库是否是hikar数据库连接池
		if (!(dataSource instanceof HikariDataSource)) {
			System.err.println("Wrong dataSource type : " + dataSource.getClass().getCanonicalName());
			System.exit(-1);
		}
	}
}
