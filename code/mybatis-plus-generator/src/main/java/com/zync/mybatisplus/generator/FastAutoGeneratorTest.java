package com.zync.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * 代码生成器
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/9/6 10:40
 */
public class FastAutoGeneratorTest {

    /**
     * 1、数据库配置
     * @return
     */
    private static DataSourceConfig.Builder initDataSourceConfig() {
        // url: jdbc路径
        // username: 数据库账号
        // password: 数据库密码
        // dbQuery: 数据库查询, new MySqlQuery(),只在SQLQuery下生效
        // schema: 数据库
        // typeConvert: 数据库类型转换器, new MySqlTypeConvert(),只在SQLQuery下生效
        // keyWordsHandler: 数据库关键字处理器
        // typeConvertHandler: 类型转换器(默认), 自定义实现ITypeConvertHandler,只在DefaultQuery下生效
        // databaseQueryClass: 数据库查询方式, 默认DefaultQuery.class(通用元数据), SQLQuery.class(SQL查询)
        return new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/study?serverTimezone=Asia/Shanghai", "root", "1qaz2wsx")
                .dbQuery(new MySqlQuery())
                .schema("study")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
    }

    public static void main(String[] args) {
        FastAutoGenerator.create(initDataSourceConfig())
                // 2、全局配置
                .globalConfig(builder -> {
                    builder.disableOpenDir() // 禁止自动打开输出目录, 默认值: true
                            .outputDir("src/main/java") // 指定代码生成的输出目录
                            .author("luocong") // 设置作者名
                            // .enableKotlin() // 开启 Kotlin 模式, 默认值: false
                            // .enableSwagger() // 开启 Swagger 模式, 默认值: false
                            .dateType(DateType.ONLY_DATE) // 设置时间类型策略
                            .commentDate("yyyy-MM-dd HH:mm:ss"); // 设置注释日期格式
                })
                // 3、包配置
                .packageConfig(builder -> {
                    builder.parent("com.zync") // 设置父包名
                            .moduleName("user") // 设置父包模块名
                            .entity("pojo.po") // 设置 Entity 包名
                            .mapper("dao") // 设置 Mapper 包名
                            .service("service") // 设置 Service 包名
                            .serviceImpl("service.impl") // 设置 Service Impl 包名
                            .xml("mappers") // 设置 Mapper XML 包名
                            .controller("controller"); // 设置 Controller 包名
                })
                // 4、模板配置
                // 5、策略配置
                .strategyConfig(builder -> {

                    builder.enableCapitalMode() // 开启大写命名
                            .addInclude("user"); // 增加表匹配


                    // 实体策略配置
                    builder.entityBuilder()
                            .enableLombok() // 开启 Lombok 模型
                            .enableTableFieldAnnotation(); // 开启生成实体时生成字段注解

                    // Mapper 策略配置
                    builder.mapperBuilder()
                            .enableBaseResultMap();

                    // Service 策略配置
                    builder.serviceBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .disableService();

                    // Controller 策略配置
                    builder.controllerBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .enableRestStyle(); // 开启生成 @RestController 控制器
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
