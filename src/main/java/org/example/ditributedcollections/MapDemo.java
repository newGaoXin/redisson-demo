package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.junit.Test;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 22:03
 * @description Map demo
 */
public class MapDemo {

    public static void main(String[] args) {
        RedissonClient redissonClient = RedissonUtil.INSTANCE;
        RMap<Object, Object> map = redissonClient.getMap("MapDemo");
        // 新增
        map.put(1, "jerry");
        map.put(2, "迪伽");
        // 如果没有新增
        map.putIfAbsent(1, "tom");

        Map<Object, Object> mapDemo = map.getAll(new HashSet<>(Arrays.asList(1, 2)));
        mapDemo.forEach((key, value) -> {
            System.out.println("key: " + key + " value: " + value);
        });

        System.exit(0);
    }

    @Test
    public void test(){

    }
}
