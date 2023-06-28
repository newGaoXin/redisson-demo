package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 22:41
 * @description set lock demo
 */
public class SetLockDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new OrderPay()).start();
        }
    }

    public static class OrderPay implements Runnable {

        @Override
        public void run() {
            RedissonClient redissonClient = RedissonUtil.INSTANCE;
            RSet<String> set = redissonClient.getSet(SetLockDemo.class.getSimpleName());
            RLock lock = set.getLock("王五");
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到锁开始下单");

                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "下单成功释放锁");
            }
        }
    }
}
