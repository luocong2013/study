package com.zync.serviceconsumer.feign;

import feign.Param;
import feign.RequestLine;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FeignClient使用Configuration
 *             Feign默认使用的是SpringMVC的注解，如果要使用Feign原生注解需要配置
 * @date 2019/4/21 18:29
 */
//@FeignClient(name = "service-provider", url = "http://127.0.0.1:8751/", configuration = FeignConfigure.class)
public interface Consumer2 {

    /**
     * hello方法
     * @param name
     * @return
     */
    @RequestLine("GET /provider/hello/{name}")
    String hello(@Param("name") String name);

}
