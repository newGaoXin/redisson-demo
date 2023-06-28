package org.example.objects;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;

import java.util.concurrent.CountDownLatch;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 09:44
 * @description AtomicLong Demo
 */
public class AtomicLongDemo {

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(10000);

    public static void main(String[] args) throws InterruptedException {
        Redisson redisson = RedissonUtil.INSTANCE;
        RAtomicLong atomicLong = redisson.getAtomicLong(AtomicLongDemo.class.getSimpleName());
        atomicLong.set(0);
        for (int i = 0; i < 10000; i++) {
            new Thread(new AtomicLongIncrementer()).start();
        }

        COUNT_DOWN_LATCH.await();
        System.out.println(atomicLong.get());
        System.exit(0);
    }

    public static class AtomicLongIncrementer implements Runnable {
        @Override
        public void run() {
            Redisson redisson = RedissonUtil.INSTANCE;
            RAtomicLong atomicLong = redisson.getAtomicLong(AtomicLongDemo.class.getSimpleName());
            atomicLong.incrementAndGet();
            COUNT_DOWN_LATCH.countDown();
        }
    }
}
