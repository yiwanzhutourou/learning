package com.youdushufang.concurrent.cache;

import com.youdushufang.concurrent.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache<K, V> {

    private final Map<K, V> m = new HashMap<>();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    public void put(K key, V value) throws InterruptedException {
        Lock writeLock = this.writeLock;
        writeLock.lockInterruptibly();
        Log.log("enter write lock...");
        try {
            m.put(key, value);
        } finally {
            Log.log("ready to exit write lock...");
            writeLock.unlock();
        }
    }

    public V get(K key) throws InterruptedException {
        Lock readLock = this.readLock;
        readLock.lockInterruptibly();
        try {
            return m.get(key);
        } finally {
            readLock.unlock();
        }
    }
}
