package org.example.objects;

import org.example.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

/**
 * @author newgaoxin
 * @version 1.0
 * @date 2023/6/28 11:24
 * @description retelimiter demo
 */
public class ReteLimiterDemo {

    private static final RRateLimiter limiter;

    static {
        Redisson redisson = RedissonUtil.INSTANCE;
        limiter = redisson.getRateLimiter(ReteLimiterDemo.class.getSimpleName());
        // 初始化 2秒钟产生5个令牌
        limiter.trySetRate(RateType.OVERALL, 5, 2, RateIntervalUnit.SECONDS);

    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(new CreateOrder()).start();
        }
        System.exit(0);
    }

    public static class CreateOrder implements Runnable {
        @Override
        public void run() {
            boolean b = limiter.tryAcquire(1);
            if (b) {
                System.out.println("创建订单成功");
            } else {
                System.out.println("创建订单失败");
            }

        }
    }
}
