package com.zync.webflux.springbootwebflux.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 城市实体类
 * @date 2020/3/7 16:43
 */
@Getter
@Setter
public class City {
    /**
     * 城市编号
     */
    private Long id;
    /**
     * 省份编号
     */
    private Long provinceId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 描述
     */
    private String description;
}
