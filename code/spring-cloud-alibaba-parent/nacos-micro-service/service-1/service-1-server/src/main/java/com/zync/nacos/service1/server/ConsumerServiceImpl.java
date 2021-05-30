package com.zync.nacos.service1.server;

import com.zync.nacos.service1.api.ConsumerService;
import com.zync.nacos.service2.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 消费者服务接口的实现
 * @date 2021/5/30 17:59
 */
@DubboService
public class ConsumerServiceImpl implements ConsumerService {

    @DubboReference
    private ProviderService providerService;

    @Override
    public String service() {
        return "Consumer Invoke | " + providerService.service();
    }
}
