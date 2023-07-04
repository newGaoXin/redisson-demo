package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.EvictionMode;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private RedissonClient redissonClient;

    @Before
    public void before() {
        redissonClient = RedissonUtil.INSTANCE;
    }

    /**
     * 驱逐
     *
     */
    @Test
    public void mapEviction() throws InterruptedException {
        RMapCache<String, String> map = redissonClient.getMapCache(MapDemo.class.getSimpleName() + "-mapEviction");


        // ttl = 10 minutes,
        map.put("key1", "我是value2", 10, TimeUnit.SECONDS);
        // ttl = 10 minutes, maxIdleTime = 10 seconds
        map.put("key2", "我是value2", 1, TimeUnit.MINUTES, 1, TimeUnit.SECONDS);

        // if object is not used anymore
        Thread.sleep(20 * 1000);
    }

    @Test
    public void mapEvictionGet(){
        RMapCache<String, String> map = redissonClient.getMapCache(MapDemo.class.getSimpleName() + "-mapEviction");
        String key2 = map.get("key2");
        System.out.println(key2);
    }

    /**
     * LRU/LFU bounded Map
     */
    @Test
    public void boundedMap() {
        RMapCache<String, String> map = redissonClient.getMapCache(MapDemo.class.getSimpleName() + "-boundedMap");

        // tries to set limit map to 10 entries using LRU eviction algorithm
        map.trySetMaxSize(10);
        // ... using LFU eviction algorithm
        map.trySetMaxSize(10, EvictionMode.LFU);

        // set or change limit map to 10 entries using LRU eviction algorithm
//        map.setMaxSize(10);
        // ... using LFU eviction algorithm
//        map.setMaxSize(10, EvictionMode.LFU);

        map.put("1", "2");
        map.put("3", "3", 10, TimeUnit.SECONDS);

        for (int i = 0; i < 100; i++) {
            map.put("" + i, "" + i);
        }

        System.out.println(map.size());
    }

    @Test
    public void listeners() throws InterruptedException {
        RMapCache<Integer, String> map = redissonClient.getMapCache(MapDemo.class.getSimpleName() + "-listeners");

        int updateListener = map.addListener(new EntryUpdatedListener<Integer, String>() {
            @Override
            public void onUpdated(EntryEvent<Integer, String> event) {
                event.getKey(); // key
                event.getValue(); // new value
                event.getOldValue(); // old value
                // ...
                System.out.println("onUpdated key:" + event.getKey() + " value:" + event.getValue() + " oldValue:" + event.getOldValue());
            }
        });

        int createListener = map.addListener(new EntryCreatedListener<Integer, String>() {
            @Override
            public void onCreated(EntryEvent<Integer, String> event) {
                event.getKey(); // key
                event.getValue(); // value
                // ...
                System.out.println("onCreated key:" + event.getKey() + " value:" + event.getValue());

            }
        });

        int expireListener = map.addListener(new EntryExpiredListener<Integer, String>() {
            @Override
            public void onExpired(EntryEvent<Integer, String> event) {
                event.getKey(); // key
                event.getValue(); // value
                // ...
                System.out.println("onExpired key:" + event.getKey() + " value:" + event.getValue());
            }
        });

        int removeListener = map.addListener(new EntryRemovedListener<Integer, String>() {
            @Override
            public void onRemoved(EntryEvent<Integer, String> event) {
                event.getKey(); // key
                event.getValue(); // value
                // ...
                System.out.println("onRemoved key:" + event.getKey() + " value:" + event.getValue());
            }
        });

        map.put(1, "迪伽");
        map.put(2, "塞文");
        map.put(1, "泰罗", 10, TimeUnit.SECONDS);

        map.remove(2);

        Thread.sleep(15000);

        map.removeListener(updateListener);
        map.removeListener(createListener);
        map.removeListener(expireListener);
        map.removeListener(removeListener);
    }
}
