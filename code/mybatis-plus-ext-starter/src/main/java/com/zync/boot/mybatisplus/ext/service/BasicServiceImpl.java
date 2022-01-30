package com.zync.boot.mybatisplus.ext.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zync.boot.mybatisplus.ext.mapper.BasicMapper;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

/**
 * 自定义IService接口实现类, 使用带扩展功能的Service需要继承此BasicServiceImpl
 * <p>
 * <pre>
 *    public class XxxServiceImpl extends BasicServiceImpl<XxxMapper, Xxx> implements XxxService {
 *
 *    }
 * </pre>
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 17:44
 */
public class BasicServiceImpl<M extends BasicMapper<T>, T> extends ServiceImpl<M, T> {

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        Assert.isTrue(entityList instanceof List, "entityList must be List.");
        List<T> list = (List<T>) entityList;

        int size = list.size();
        int count = size / batchSize;
        if (size % batchSize != 0) {
            count++;
        }
        int row = 0;
        for (int i = 0; i < count; i++) {
            int start = i * batchSize;
            int end = Math.min(start + batchSize, size);
            List<T> subList = list.subList(start, end);
            row += baseMapper.insertBatchSomeColumn(subList);
        }
        return size == row;
    }
}
