package com.zync.boot.mybatisplus.ext.configuration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * mybatis plus ext import selector
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/6/27 18:16
 */
public final class MybatisPlusExtImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {"com.zync.boot.mybatisplus.ext.configuration.MybatisPlusExtConfiguration"};
    }

}
