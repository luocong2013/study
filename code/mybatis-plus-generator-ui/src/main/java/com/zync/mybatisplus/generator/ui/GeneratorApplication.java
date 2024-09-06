package com.zync.mybatisplus.generator.ui;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;

/**
 * 程序入口
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/9/6 16:30
 */
public class GeneratorApplication {

    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://localhost:3306/study?serverTimezone=Asia/Shanghai")
                .userName("root").password("1qaz2wsx").driverClassName("com.mysql.cj.jdbc.Driver")
                .nameConverter(new NameConverter() {
                    @Override
                    public String serviceNameConvert(String entityName) {
                        return entityName + "Service";
                    }
                }).basePackage("com.zync").port(8068).build();
        MybatisPlusToolsApplication.run(config);
    }

}
