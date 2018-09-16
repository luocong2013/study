package com.ccyw.springboot.own.common.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author luoc
 * @version V1.0.0
 * @description 继承自己的MyMapper
 * @date 2018/8/19 17:36
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
