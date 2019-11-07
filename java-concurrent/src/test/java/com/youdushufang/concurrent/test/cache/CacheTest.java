package com.youdushufang.concurrent.test.cache;

import com.youdushufang.concurrent.cache.Cache;
import com.youdushufang.concurrent.cache.StampedCache;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

class CacheTest {

    @Test
    void testCache() throws InterruptedException {
        Cache<String, String> cache = new Cache<>();

        new Thread(() -> {
            try {
                cache.put("hello", "World");
                Thread.sleep(100);
                cache.put("hello", "KingCrush");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        for (int i = 0; i < 10000; i++) {
            Log.log("cache get: " + cache.get("hello"));
        }
    }

    @Test
    void testStampedCache() throws InterruptedException {
        StampedCache<String, String> cache = new StampedCache<>();

        new Thread(() -> {
            try {
                cache.put("hello", "World");
                Thread.sleep(100);
                cache.put("hello", "KingCrush");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        for (int i = 0; i < 10000; i++) {
            Log.log("cache optimistic get: " + cache.optimisticGet("hello"));
            Log.log("cache get: " + cache.get("hello"));
        }
    }
}
