package com.zync.zookeeper.lock;

import com.zync.zookeeper.TestingServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 信号量 - Shared Semaphore
 *              一个计数的信号量类似JDK的Semaphore。 JDK中Semaphore维护的一组许可(permits)，
 *              而Curator中称之为租约(Lease)。 有两种方式可以决定semaphore的最大租约数。
 *              第一种方式是用户给定path并且指定最大LeaseSize。第二种方式用户给定path并且使用
 *              SharedCountReader类。如果不使用SharedCountReader，必须保证所有实例在多进程中
 *              使用相同的(最大)租约数量，否则有可能出现A进程中的实例持有最大租约数量为10，
 *              但是在B进程中持有的最大租约数量为20，此时租约的意义就失效了。
 * @date 2020/6/2 17:35
 */
public class InterProcessSemaphoreDemo {
    private static final int MAX_LEASE = 10;
    private static final String PATH = "/examples/locks";

    public static void main(String[] args) throws Exception {
        FakeLimitedResource resource = new FakeLimitedResource();
        try {

            CuratorFramework client = TestingServer.getClient();
            client.start();

            InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, PATH, MAX_LEASE);
            Collection<Lease> leases = semaphore.acquire(5);
            System.out.println("get " + leases.size() + " leases");
            Lease lease = semaphore.acquire();
            System.out.println("get another lease");

            resource.use();

            Collection<Lease> leases2 = semaphore.acquire(5, 10, TimeUnit.SECONDS);
            System.out.println("Should timeout and acquire return " + leases2);

            System.out.println("return one lease");
            semaphore.returnLease(lease);
            System.out.println("return another 5 leases");
            // 归还租约
            semaphore.returnAll(leases);
        } finally {
            // ignore
        }
    }
}
