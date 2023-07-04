package org.example.ditributedcollections;

import net.bytebuddy.implementation.bytecode.ShiftLeft;
import org.example.objects.ObjectHolderDemo;
import org.example.utils.RedissonUtil;
import org.junit.Before;
import org.junit.Test;
import org.redisson.RedissonDelayedQueue;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/29 13:53
 * @description TODO
 */
public class DelayedQueueDemo {

    private static RedissonClient REDISSON_CLIENT;

    private static RDelayedQueue<Object> delayedQueue;

    private static RBlockingDeque<Object> blockingDeque;

    @Before
    public void before() {
        REDISSON_CLIENT = RedissonUtil.INSTANCE;
        blockingDeque = REDISSON_CLIENT.getBlockingDeque(DelayedQueueDemo.class.getSimpleName());
        delayedQueue = REDISSON_CLIENT.getDelayedQueue(blockingDeque);
    }

    @Test
    public void offer() {
        ObjectHolderDemo.Human s = new ObjectHolderDemo.Human("msg11");
        ObjectHolderDemo.Human s1 = new ObjectHolderDemo.Human("msg11");
        System.out.println(s == s1);
        // move object to distinationQueue in 10 seconds
        delayedQueue.offer(s, 3, TimeUnit.SECONDS);
        // move object to distinationQueue in 1 minutes
        delayedQueue.offer("msg22", 10, TimeUnit.SECONDS);

        boolean remove = delayedQueue.remove(s1);
        System.out.println(remove);
    }

    @Test
    public void poll() throws InterruptedException {
        while (true) {
            Object poll = blockingDeque.poll();
            if (Objects.isNull(poll)) {
                Thread.sleep(1);
                continue;
            }
            System.out.println(poll);
        }
    }

    @Test
    public void peek() throws InterruptedException {
        while (true) {
            Object peek = blockingDeque.peek();
            if (Objects.isNull(peek)) {
                Thread.sleep(1);
                continue;
            }
            System.out.println(peek);
            boolean remove = blockingDeque.remove(peek);
            System.out.println(remove);
        }
    }
}
