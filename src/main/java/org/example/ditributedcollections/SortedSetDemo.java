package org.example.ditributedcollections;

import org.example.utils.RedissonUtil;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RSortedSet;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/7/4 17:23
 * @description TODO
 */
public class SortedSetDemo {

    private RSortedSet<Integer> sortedSet;

    @Before
    public void before() {
        Redisson redisson = RedissonUtil.INSTANCE;
        sortedSet = redisson.getSortedSet(SoredSortedSetDemo.class.getSimpleName());
    }

    @Test
    public void add() {
        sortedSet.add(1);
        sortedSet.add(3);
        sortedSet.add(0);
        System.out.println("添加后的元素：");
        for (Object o : sortedSet) {
            System.out.println(o);
        }
    }

    @Test
    public void getFirst(){
        this.add();
        Integer first = sortedSet.first();
        System.out.println("获取到的第一个元素"+first);
    }

    @Test
    public void stringType(){
        RLexSortedSet lexSortedSet = RedissonUtil.INSTANCE.getLexSortedSet(SoredSortedSetDemo.class.getSimpleName() + "-LexSortedSet");
        lexSortedSet.add("a");
        lexSortedSet.add("z");
        lexSortedSet.add("u");
        System.out.println("添加后的元素：");
        lexSortedSet.forEach(System.out::println);

        // 获取第一个元素
        System.out.println("获取第一个元素："+lexSortedSet.first());


    }
}
