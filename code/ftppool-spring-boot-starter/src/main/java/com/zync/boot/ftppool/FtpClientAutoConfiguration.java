package com.zync.boot.ftppool;

import com.zync.boot.ftppool.client.FtpClientProperties;
import com.zync.boot.ftppool.service.FtpClientService;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption FtpClient自动配置类
 * @date 2020/2/12 15:23
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(FTPClient.class)
@EnableConfigurationProperties(FtpClientProperties.class)
public class FtpClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = FtpClientProperties.PREFIX, value = "enabled", havingValue = "true")
    public FtpClientService ftpClientService(FtpClientProperties properties) {
        return new FtpClientService(properties);
    }
}
