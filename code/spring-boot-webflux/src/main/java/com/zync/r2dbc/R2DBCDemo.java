package com.zync.r2dbc;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * r2dbc demo
 *
 * @author luocong
 * @version v2.5.0
 * @since 2025/1/8 15:54
 */
public class R2DBCDemo {

    public static void main(String[] args) throws IOException {
        //ConnectionFactory factory = ConnectionFactories.get("r2dbc:mysql://root:1qaz2wsx@localhost:3306/study");

        //0、MySQL配置
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("1qaz2wsx")
                .database("study")
                .build();
        //1、获取连接工厂
        MySqlConnectionFactory factory = MySqlConnectionFactory.from(configuration);

        //2、获取到连接，发送sql
        //JDBC： Statement： 封装sql的
        //3、数据发布者
        Mono.from(factory.create())
                .flatMapMany(connection -> connection
                                .createStatement("select * from user where name = ?name")
                                .bind("name", "张三")
                                .execute()
                        )
                .flatMap(result -> result.map((row, rowMetadata) -> row.get("email", String.class)))
                .subscribe(v -> System.out.println("v = " + v));

        System.in.read();
    }
}
