package com.zync.http.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Demo Api
 *
 * @author luocong
 * @version v1.0
 * @date 2022/12/12 20:33
 */
@HttpExchange
public interface DemoApi {

    /**
     * 获取天气接口
     * CityCode:
     * 成都: 101030100
     * 双流区: 101270106
     * @param cityCode
     * @return
     */
    @GetExchange("/api/weather/city/{cityCode}")
    String weather(@PathVariable("cityCode") String cityCode);

}
