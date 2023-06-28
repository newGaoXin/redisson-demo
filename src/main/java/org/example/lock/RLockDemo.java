package org.example.lock;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 13:18
 * @description rlock demo
 */
public class RLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Redisson redisson = RedissonUtil.INSTANCE;
        RLock lock = redisson.getLock("myLock");

        // traditional lock method
        lock.lock();

        // or acquire lock and automatically unlock it after 10 seconds
        lock.lock(10, TimeUnit.SECONDS);

        // or wait for lock aquisition up to 100 seconds
        // and automatically unlock it after 10 seconds
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if (res) {
            try {
                System.out.println("获取锁成功");
                // 业务
            } finally {
                lock.unlock();
                System.out.println("释放锁成功");
            }
        }
        System.exit(0);
    }
}
