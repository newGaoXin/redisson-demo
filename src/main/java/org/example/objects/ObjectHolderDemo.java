package org.example.objects;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RDelayedQueue;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 23:12
 * @description 对象桶
 */
public class ObjectHolderDemo {

    public static void main(String[] args) {
        Redisson redisson = RedissonUtil.INSTANCE;

        RBucket<Human> bucket = redisson.getBucket(ObjectHolderDemo.class.getSimpleName());

        bucket.set(new Human("张三"));
        Human obj = bucket.get();
        System.out.println(obj);

        // 如果不存在设置
        bucket.setIfAbsent(new Human("李四"));
        System.out.println("如果不存在设置后的结果"+bucket.get());

        // 比较并交换
        bucket.compareAndSet(new Human("张三"), new Human("张三"));
        System.out.println("比较并交换后的结果"+bucket.get());

        System.exit(0);
    }

    /**
     * 人
     */
    public static class Human {
        private String name;

        public Human(String name) {
            this.name = name;
        }
    }
}
