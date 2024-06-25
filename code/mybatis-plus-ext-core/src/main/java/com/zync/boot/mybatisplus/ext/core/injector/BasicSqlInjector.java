package com.zync.boot.mybatisplus.ext.core.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.zync.boot.mybatisplus.ext.core.annotation.IgnoreInsert;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 自定义SqlInjector
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 10:18
 */
public class BasicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn(predicate()));
        return methodList;
    }

    /**
     * 字段筛选条件
     * @return Predicate
     */
    private Predicate<TableFieldInfo> predicate() {
        return tableField -> {
            Field field = tableField.getField();
            IgnoreInsert annotation = field.getAnnotation(IgnoreInsert.class);
            return Objects.isNull(annotation);
        };
    }

}
