package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 22:33
 * @description set demo
 */
public class SetDemo {

    public static void main(String[] args) {
        RedissonClient redissonClient = RedissonUtil.INSTANCE;
        RSet<String> set = redissonClient.getSet("anySet");
        set.add("张三");
        set.forEach(System.out::println);
        set.remove("张三");
        int size = set.size();
        System.out.println("----- 华丽的分割线 ------");
        System.out.println("size = " + size);


        System.exit(0);
    }
}
