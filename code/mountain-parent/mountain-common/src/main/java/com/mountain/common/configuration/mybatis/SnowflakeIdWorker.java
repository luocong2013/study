package com.mountain.common.configuration.mybatis;

/**
 * 雪花算法工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/01/23 13:25
 **/
public class SnowflakeIdWorker {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）(2026-01-01 00:00:00)
     */
    private static final long START_TIMESTAMP = 1767196800000L;

    /**
     * 序列号占用位数
     */
    private static final long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用位数
     */
    private static final long MACHINE_BIT = 5;
    /**
     * 数据中心占用位数
     */
    private static final long DATACENTER_BIT = 5;

    /**
     * 每部分的最大值
     */
    private static final long MAX_SEQUENCE_NUM = ~(-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    /**
     * 每部分向左的位移
     */
    private static final long MACHINE_LEFT_SHIFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;

    /**
     * 数据中心标识
     */
    private final long datacenterId;
    /**
     * 机器标识
     */
    private final long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 构造方法
     *
     * @param datacenterId 数据中心标识，取值范围 [0, 31]
     * @param machineId    机器标识，取值范围 [0, 31]
     */
    public SnowflakeIdWorker(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter Id cannot be greater than " + MAX_DATACENTER_NUM + " or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("Machine Id cannot be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一个ID
     *
     * @return long类型的ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            if (sequence == 0) {
                // 毫秒内序列溢出，阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_LEFT_SHIFT)
                | (machineId << MACHINE_LEFT_SHIFT)
                | sequence;
    }

    /**
     * 生成下一个ID
     *
     * @return String类型的ID
     */
    public synchronized String nextIdStr() {
        return String.valueOf(nextId());
    }

    /**
     * 若当前毫秒的序列号已经用完，则等待下一个毫秒
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 下一个毫秒的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 反解id的时间戳部分
     */
    public static long parseIdTimestamp(long id) {
        return (id >> 22) + START_TIMESTAMP;
    }

}
