package com.zync.single.security.memory.singlesecuritymemory.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author 01396453(luocong)
 * @version 3.0.7
 * @description 验证码配置类
 * @date 2020/12/22 18:29
 */
@SpringBootConfiguration
public class VerifyCodeConfig {

    @Bean
    public Producer verifyCode() {
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "50");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789abcdefghijklmnopqrstuvwxyz");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,华文楷体,华文行楷");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "255,0,0");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
