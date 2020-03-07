package com.zync.webflux.springbootwebflux.map.dap;

import com.zync.webflux.springbootwebflux.common.domain.City;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 城市数据访问层
 * @date 2020/3/7 16:46
 */
@Repository
public class CityRepository {

    private ConcurrentMap<Long, City> repository = new ConcurrentHashMap<>(16);

    private static final AtomicLong idGenerator = new AtomicLong(0);

    public Long save(City city) {
        Long id = idGenerator.incrementAndGet();
        city.setId(id);
        repository.put(id, city);
        return id;
    }

    public Collection<City> findAll() {
        return repository.values();
    }

    public City findCityById(Long id) {
        return repository.get(id);
    }

    public Long updateCity(City city) {
        repository.put(city.getId(), city);
        return city.getId();
    }

    public Long deleteCity(Long id) {
        repository.remove(id);
        return id;
    }
}
