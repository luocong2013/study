package com.zync.gatewayzuul.configure;

import com.zync.gatewayzuul.filter.CustomErrorFilter;
import com.zync.gatewayzuul.filter.CustomPostFilter;
import com.zync.gatewayzuul.filter.CustomPreFilter;
import com.zync.gatewayzuul.filter.CustomRouteFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Filter配置类
 * @date 2019/6/1 23:26
 */
@SpringBootConfiguration
public class FilterConfigure {

    @Bean
    public CustomPreFilter customPreFilter() {
        return new CustomPreFilter();
    }

    @Bean
    public CustomPostFilter customPostFilter() {
        return new CustomPostFilter();
    }

    @Bean
    public CustomRouteFilter customRouteFilter() {
        return new CustomRouteFilter();
    }

    @Bean
    public CustomErrorFilter customErrorFilter() {
        return new CustomErrorFilter();
    }
}
