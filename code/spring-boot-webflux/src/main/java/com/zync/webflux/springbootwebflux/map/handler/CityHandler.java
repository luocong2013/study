package com.zync.webflux.springbootwebflux.map.handler;

import com.zync.webflux.springbootwebflux.common.domain.City;
import com.zync.webflux.springbootwebflux.map.dap.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 城市处理类
 *          Mono: 实现发布者，并返回0或1个元素，即单对象
 *          Flux: 实现发布者，并返回N个元素，即List列表对象
 *
 *          Mono常用的方法有：
 *            Mono.create() : 使用MonoSink来创建Mono
 *            Mono.justOrEmpty() : 从一个Optional对象或null对象中创建Mono
 *            Mono.error() : 创建一个只包含错误消息的Mono
 *            Mono.never() : 创建一个不包含任何消息通知的Mono
 *            Mono.delay() : 在指定的延迟时间之后，创建一个Mono，产生数字0作为唯一值
 * @date 2020/3/7 16:15
 */
@Component
public class CityHandler {

    @Autowired
    private CityRepository cityRepository;

    /**
     * 处理器类，ServerResponse是对响应的封装
     * @param request
     * @return
     */
    public Mono<ServerResponse> helloCity(ServerRequest request) {
        City city = new City();
        city.setCityName("成都");
        Mono<City> mono = Mono.just(city);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(mono, City.class);
    }

    public Mono<Long> save(City city) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityRepository.save(city)));
    }

    public Mono<City> findCityById(Long id) {
        return Mono.justOrEmpty(cityRepository.findCityById(id));
    }

    public Flux<City> findAllCity() {
        return Flux.fromIterable(cityRepository.findAll());
    }

    public Mono<Long> modifyCity(City city) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityRepository.updateCity(city)));
    }

    public Mono<Long> deleteCity(Long id) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityRepository.deleteCity(id)));
    }
}
