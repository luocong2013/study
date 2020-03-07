package com.zync.webflux.springbootwebflux.map.controller;

import com.zync.webflux.springbootwebflux.common.domain.City;
import com.zync.webflux.springbootwebflux.map.handler.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 城市控制器
 * @date 2020/3/7 17:20
 */
@RestController
@RequestMapping("/map/city")
public class CityController {

    @Autowired
    private CityHandler cityHandler;

    @GetMapping("/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return cityHandler.findCityById(id);
    }

    @GetMapping()
    public Flux<City> findAllCity() {
        return cityHandler.findAllCity();
    }

    @PostMapping()
    public Mono<Long> saveCity(@RequestBody City city) {
        return cityHandler.save(city);
    }

    @PutMapping()
    public Mono<Long> modifyCity(@RequestBody City city) {
        return cityHandler.modifyCity(city);
    }

    @DeleteMapping("/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return cityHandler.deleteCity(id);
    }
}
