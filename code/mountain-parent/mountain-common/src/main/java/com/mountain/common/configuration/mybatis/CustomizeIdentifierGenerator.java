package com.mountain.common.configuration.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 自定义IdentifierGenerator
 *
 * @author luocong
 * @version 1.0
 * @since 2026/1/23 14:39
 **/
public class CustomizeIdentifierGenerator implements IdentifierGenerator {

    private final SnowflakeIdWorker snowflakeIdWorker;

    public CustomizeIdentifierGenerator() {
        this.snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
    }

    public CustomizeIdentifierGenerator(long datacenterId, long machineId) {
        this.snowflakeIdWorker = new SnowflakeIdWorker(datacenterId, machineId);
    }

    public CustomizeIdentifierGenerator(SnowflakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }

    @Override
    public Number nextId(Object entity) {
        return snowflakeIdWorker.nextId();
    }

}
