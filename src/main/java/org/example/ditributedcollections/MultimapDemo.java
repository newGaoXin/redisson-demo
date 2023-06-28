package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 22:23
 * @description Multimap Demo
 */
public class MultimapDemo {

    public static void main(String[] args) {
        RedissonClient redissonClient = RedissonUtil.INSTANCE;

        RSetMultimap<Long, String> multimap = redissonClient.getSetMultimap(MultimapDemo.class.getSimpleName());
        multimap.put(0L, "迪伽");
        multimap.put(0L, "泰罗");
        multimap.put(3L, "tom");

        Set<String> allValues = multimap.get(Long.valueOf(0));
        System.out.println("0号键的所有值：" + allValues);

        List<String> newValues = Arrays.asList("虹猫", "蓝兔", "黑小虎");
        Set<String> oldValues = multimap.replaceValues(0L, newValues);
        System.out.println("0号键被替换的值：" + oldValues);

        Set<String> removedValues = multimap.removeAll(0L);
        System.out.println("0号键被移除的所有值：" + removedValues);
    }
}
