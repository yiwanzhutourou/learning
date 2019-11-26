package com.youdushufang.lock;

import com.youdushufang.jedis.JedisManager;
import org.junit.jupiter.api.Test;

public class ReentrantRedisLockTest {

    @Test
    public void testReentrantRedisLock() {
        ReentrantRedisLock lock = new ReentrantRedisLock(JedisManager.getInstance());
        String key = "com:youdushufang:lock:ReentrantRedisLockTest:testReentrantRedisLock";
        if (lock.lock(key)) {
            try {
                System.out.println("lock once");
                if (lock.lock(key)) {
                    try {
                        System.out.println("lock twice");
                    } finally {
                        lock.unlock(key);
                    }
                }
            } finally {
                lock.unlock(key);
            }
        }
    }
}
