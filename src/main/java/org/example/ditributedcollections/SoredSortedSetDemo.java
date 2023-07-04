package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSortedSet;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/27 22:52
 * @description TODO
 */
public class SoredSortedSetDemo {

    public static void main(String[] args) {
        Redisson instance = RedissonUtil.INSTANCE;
        RScoredSortedSet<String> set = instance.getScoredSortedSet(SoredSortedSetDemo.class.getSimpleName());

        set.add(0.6, "张三");
        set.addAsync(0.251, "李四");
        set.add(0.302, "王五");
        set.add(0.4, "赵六");

        System.out.println("----- 遍历 ------");
        set.forEach(System.out::println);

        System.out.println("----- 移除第一个 ------");
        set.pollFirst();
        set.forEach(System.out::println);
        System.out.println("----- 移除最后一个 ------");
        set.pollLast();
        set.forEach(System.out::println);

        System.out.println("----- 华丽的分割线 ------");
        int index = set.rank("王五"); // get element index
        System.out.println("王五的排名：" + index);
        Double score = set.getScore("王五"); // get element score
        System.out.println("王五的分数：" + score);

        System.exit(0);
    }
}
