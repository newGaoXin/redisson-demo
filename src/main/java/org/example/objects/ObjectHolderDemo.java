package org.example.objects;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;

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

//        bucket.trySet(new AnyObject(3));
//        bucket.compareAndSet(new AnyObject(4), new AnyObject(5));
//        bucket.getAndSet(new AnyObject(6));

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

        @Override
        public String toString() {
            return "Human{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
