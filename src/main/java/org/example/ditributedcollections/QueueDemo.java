package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.junit.Before;
import org.redisson.Redisson;
import org.redisson.api.RQueue;

import java.util.Objects;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/29 15:47
 * @description TODO
 */
public class QueueDemo {

    public static void main(String[] args) {
        Redisson redisson = RedissonUtil.INSTANCE;

        // 新增
        RQueue<String> queue = redisson.getQueue(QueueDemo.class.getSimpleName());
        queue.add("value1");


        // peek
        String obj = queue.peek();
        System.out.println("peek = " + obj);

        // poll
        String someObj = queue.poll();
        System.out.println("poll = " + someObj);

    }
}
