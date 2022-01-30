package com.zync.boot.mybatisplus.ext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * 自定义BaseMapper, 使用带扩展功能的Mapper需要继承此BasicMapper
 * <p>
 * <pre>
 *     public interface XxxMapper extends BasicMapper<Xxx> {
 *
 *     }
 * </pre>
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 17:40
 */
public interface BasicMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * @param entityList 实体对象列表
     * @return 插入成功条数
     */
    int insertBatchSomeColumn(@Param("list") Collection<T> entityList);
}
