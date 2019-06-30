package com.zync.ibed.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 自定义Mapper
 * @date 2019/6/30 19:59
 */
public interface CustomMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
