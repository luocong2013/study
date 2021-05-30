package com.zync.nacos.service2.server;

import com.zync.nacos.service2.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 生产者服务接口的实现
 * @date 2021/5/30 18:21
 */
@DubboService
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String service() {
        return "Provider Invoke ";
    }
}
