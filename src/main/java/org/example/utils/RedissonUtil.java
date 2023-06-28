package org.example.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 21:59
 * @description TODO
 */
public class RedissonUtil {

    public static final Redisson INSTANCE;

    static {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://:6379");
        INSTANCE = (Redisson) Redisson.create(config);
    }

    public static RedissonClient getRedissonStackClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://:6380");
        return Redisson.create(config);
    }
}
