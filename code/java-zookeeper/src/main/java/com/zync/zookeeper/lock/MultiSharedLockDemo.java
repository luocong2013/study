package com.zync.zookeeper.lock;

import com.zync.zookeeper.TestingServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 多共享锁对象 - Multi Shared Lock
 *              Multi Shared Lock是一个锁的容器。 当调用acquire()， 所有的锁都会被acquire()，
 *              如果请求失败，所有的锁都会被release。 同样调用release时所有的锁都被release(失败被忽略)。
 *              基本上，它就是组锁的代表，在它上面的请求释放操作都会传递给它包含的所有的锁。
 * @date 2020/6/2 17:41
 */
public class MultiSharedLockDemo {

    private static final String PATH1 = "/examples/locks1";
    private static final String PATH2 = "/examples/locks2";

    public static void main(String[] args) throws Exception {
        FakeLimitedResource resource = new FakeLimitedResource();
        try {
            CuratorFramework client = TestingServer.getClient();
            client.start();

            InterProcessLock lock1 = new InterProcessMutex(client, PATH1);
            InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, PATH2);

            InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));

            if (!lock.acquire(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("could not acquire the lock");
            }
            System.out.println("has got all lock");

            System.out.println("has got lock1: " + lock1.isAcquiredInThisProcess());
            System.out.println("has got lock2: " + lock2.isAcquiredInThisProcess());

            try {
                resource.use(); //access resource exclusively
            } finally {
                System.out.println("releasing the lock");
                lock.release(); // always release the lock in a finally block
            }
            System.out.println("has got lock1: " + lock1.isAcquiredInThisProcess());
            System.out.println("has got lock2: " + lock2.isAcquiredInThisProcess());
        } finally {
            // ignore
        }
    }
}
