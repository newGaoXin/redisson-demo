package org.example.lock;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 14:42
 * @description Semaphore demo
 */
public class SemaphoreDemo {

    private static final RSemaphore semaphore;

    static {
        Redisson redisson = RedissonUtil.INSTANCE;
        // 最多允许两个线程同时执行
        semaphore = redisson.getSemaphore(SemaphoreDemo.class.getSimpleName());
        // 初始化信号量
        semaphore.trySetPermits(1);
    }

    /**
     * 业务常见场景：下单限流
     *
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(new Task()).start();
        }
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                // 获取信号量
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "获取信号量成功");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放信号量
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + "释放信号量成功");
            }
        }
    }
}
