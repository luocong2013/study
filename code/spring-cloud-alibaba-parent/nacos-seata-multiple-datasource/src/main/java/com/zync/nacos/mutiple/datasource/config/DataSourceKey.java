package com.zync.nacos.mutiple.datasource.config;

import lombok.Getter;

/**
 * DataSource Key
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/19 17:44
 */
@Getter
public enum DataSourceKey {
    /**
     * Stock data source key.
     */
    STOCK,
    /**
     * Pay data source key.
     */
    PAY,
    /**
     * Order data source key.
     */
    ORDER,

}
