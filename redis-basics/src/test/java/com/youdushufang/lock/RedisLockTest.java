package com.youdushufang.lock;

import com.youdushufang.jedis.JedisManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

@Slf4j
public class RedisLockTest {

    private static final String LOCK_KEY = "com:youdushufang:lock:RedisLockTest";

    private static JedisManager jedisManager;

    private RedisLock redisLock;

    @BeforeAll
    public static void create() {
        jedisManager = JedisManager.getInstance();
    }

    @AfterAll
    public static void destroy() {
        jedisManager.shutdown();
    }

    @BeforeEach
    public void init() {
        this.redisLock = new RedisLock(jedisManager);
    }

    @Test
    public void testRedisLock() {
        System.out.println(
                redisLock.tryLock(LOCK_KEY + ":testRedisLock", "1",
                        () -> log.info("run in redis lock")));
        System.out.println(
                redisLock.tryLock(LOCK_KEY + ":testRedisLock", "1",
                        () -> log.info("run in redis lock")));
    }

    @Test
    public void testMultiThreadedAdd() {
        int[] count = new int[1];
        Stream.generate(() ->  1)
                .limit(1000)
                .parallel()
                .forEach(i -> {
                    redisLock.tryLock(LOCK_KEY + ":testMultiThreadedAdd", Thread.currentThread().getName(),
                            () -> count[0] += i);
                });
        System.out.println(count[0]);
    }
}
