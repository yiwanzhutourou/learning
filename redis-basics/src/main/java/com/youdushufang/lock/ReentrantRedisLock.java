package com.youdushufang.lock;

import com.youdushufang.jedis.JedisManager;

import java.util.HashMap;
import java.util.Map;

public class ReentrantRedisLock {

    private static final int TIMEOUT = 10; // seconds

    private ThreadLocal<Map<String, Integer>> lockerCount = new ThreadLocal<>();

    private final JedisManager jedisManager;

    public ReentrantRedisLock(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public boolean lock(String key) {
        Map<String, Integer> currentLockerCount = currentLockerCount();
        Integer count = currentLockerCount.get(key);
        if (count != null) {
            currentLockerCount.put(key, count + 1);
            return true;
        }
        boolean locked = lockInternal(key);
        if (locked) {
            currentLockerCount.put(key, 1);
            return true;
        }
        return false;
    }

    public boolean unlock(String key) {
        Map<String, Integer> currentLockerCount = currentLockerCount();
        Integer count = currentLockerCount.get(key);
        if (count == null) {
            return false;
        }
        count -= 1;
        if (count > 0) {
            currentLockerCount.put(key, count);
        } else {
            currentLockerCount.remove(key);
            unlockInternal(key);
        }
        return true;
    }

    private Map<String, Integer> currentLockerCount() {
        Map<String, Integer> refs = lockerCount.get();
        if (refs != null) {
            return refs;
        }
        lockerCount.set(new HashMap<>());
        return lockerCount.get();
    }

    // 简化问题，只提供加锁的 key，值都设置为 1
    private boolean lockInternal(String key) {
        return jedisManager.setIfNotExists(key, "1", TIMEOUT);
    }

    private void unlockInternal(String key) {
        jedisManager.del(key);
    }
}
