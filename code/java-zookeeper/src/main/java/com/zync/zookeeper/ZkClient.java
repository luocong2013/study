package com.zync.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ZkClient
 *
 * @author luocong
 * @version 1.0.0
 * @description zookeeper客户端
 * @date 2020年02月24日 9:35
 */
public class ZkClient {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.0.111:2181")
                // 会话超时时间
                .sessionTimeoutMs(5000)
                // 连接超时时间
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // 包含隔离名称
                .namespace("base")
                .build();
        client.start();

        // ① 创建数据节点
        client.create()
                // 递归创建所需父节点
                .creatingParentContainersIfNeeded()
                // 创建类型为持久节点
                .withMode(CreateMode.PERSISTENT)
                // 目录及名称
                .forPath("/nodeA", "init".getBytes());

        // ② 删除数据节点
        client.delete()
                // 强制保证删除
                .guaranteed()
                // 递归删除子节点
                .deletingChildrenIfNeeded()
                // 指定删除的版本号
                .withVersion(10086)
                .forPath("/nodeA");

        // ③ 读取数据节点
        byte[] bytes = client.getData().forPath("/nodeA");
        System.out.println(new String(bytes));

        // ④ 读取stat
        Stat stat = new Stat();
        client.getData()
                .storingStatIn(stat)
                .forPath("/nodeA");

        // ⑤ 修改数据节点
        client.setData()
                // 修改指定版本
                .withVersion(10086)
                .forPath("/nodeA", "data".getBytes());

        // ⑥ 事物
        client.inTransaction().check().forPath("/nodeA")
                .and()
                // 创建临时节点
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeB", "init".getBytes())
                .and()
                // 创建临时有序节点
                .create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/nodeC", "init".getBytes())
                .and()
                // 创建持久节点
                .create().withMode(CreateMode.PERSISTENT).forPath("/nodeD", "init".getBytes())
                .and()
                // 创建持久有序节点
                .create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/nodeE", "init".getBytes())
                .and()
                .commit();

        // ⑦ 其他
        // 检查是否存在
        client.checkExists()
                .forPath("/nodeA");
        // 获取子节点的路径
        client.getChildren().forPath("/nodeA");


        // ⑧ 异步回调
        Executor executor = Executors.newFixedThreadPool(2);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> System.out.println(String.format("eventType: %s, resultCode: %s", curatorEvent.getType(), curatorEvent.getResultCode())), executor)
                .forPath("/path");
    }
}
