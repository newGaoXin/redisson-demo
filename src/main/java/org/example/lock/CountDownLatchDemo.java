package org.example.lock;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;

import java.util.Random;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 14:51
 * @description CountDownLatch demo
 */
public class CountDownLatchDemo {

    private static final RCountDownLatch latch;

    static {
        Redisson redisson = RedissonUtil.INSTANCE;
        latch = redisson.getCountDownLatch(CountDownLatchDemo.class.getSimpleName());
        latch.trySetCount(10);
    }

    /**
     * 业务常见场景：等待所有线程执行完毕
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 随机睡眠一到两秒
                    Thread.sleep(random.nextInt(1000) + 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "执行完毕");
                latch.countDown();
            }).start();
        }

        // 等待所有线程执行完毕
        latch.await();

        System.out.println("所有线程执行完毕");
        System.exit(0);
    }


}
