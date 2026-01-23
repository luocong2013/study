package com.mountain.common.configuration.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

/**
 * 自定义MybatisPlusPropertiesCustomizer
 *
 * @author luocong
 * @version 1.0
 * @since 2026/1/23 15:24
 **/
@Component
public class CustomizeMybatisPlusPropertiesCustomizer implements MybatisPlusPropertiesCustomizer {

    @Override
    public void customize(MybatisPlusProperties properties) {
        GlobalConfig globalConfig = properties.getGlobalConfig();
        GlobalConfig.Sequence sequence = globalConfig.getSequence();
        final IdentifierGenerator identifierGenerator;
        if (sequence.getWorkerId() != null && sequence.getDatacenterId() != null) {
            identifierGenerator = new CustomizeIdentifierGenerator(sequence.getDatacenterId(), sequence.getWorkerId());
        } else {
            identifierGenerator = new CustomizeIdentifierGenerator();
        }
        // 替换默认ID生成器
        // 包括IdWorker的默认ID生成器 @see com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder.build
        globalConfig.setIdentifierGenerator(identifierGenerator);
    }
}
