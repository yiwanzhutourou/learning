package com.youdushufang.lock;

import com.youdushufang.jedis.JedisManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLock {

    private static final int TIMEOUT = 10; // seconds

    private final JedisManager jedisManager;

    public RedisLock(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public boolean tryLock(String key, String value, Runnable runnable) {
        long startTime = System.currentTimeMillis();
        long timeoutMillis = TIMEOUT * 1000L;
        do {
            // 获取分布式锁
            boolean locked = lock(key, value);
            if (locked) {
                // 获取成功后执行逻辑，finally 代码块里释放锁
                try {
                    runnable.run();
                    return true;
                } finally {
                    unlock(key, value);
                }
            } else {
                // 获取锁失败后睡一秒再尝试
                sleep(1);
            }
        } while (startTime + timeoutMillis > System.currentTimeMillis());
        log.warn("tryLock failed, key = {}, value = {}", key, value);
        return false;
    }

    private void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean lock(String key, String value) {
        return jedisManager.setIfNotExists(key, value, TIMEOUT);
    }

    private void unlock(String key, String value) {
        jedisManager.delIfEquals(key, value);
    }
}
