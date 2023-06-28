package org.example.objects;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RIdGenerator;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 10:22
 * @description id生成器 demo
 */
public class IdGeneratorDemo {

    public static void main(String[] args) {
        Redisson redisson = RedissonUtil.INSTANCE;

        RIdGenerator generator = redisson.getIdGenerator(IdGeneratorDemo.class.getSimpleName());

        // Initialize with start value = 12 and allocation size = 20000
//        generator.tryInit(12, 20000);

        long id = generator.nextId();

        long l = generator.nextId();

        System.out.println(id);
        System.out.println(l);

        System.exit(0);
    }
}
