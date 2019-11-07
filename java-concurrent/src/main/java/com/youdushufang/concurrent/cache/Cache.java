package com.youdushufang.concurrent.cache;

import com.youdushufang.concurrent.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 读写锁，允许多个线程同时获取读锁，只允许一个线程获取写锁
// 写锁和读锁互斥，有线程在读时，写阻塞，有线程在写时，读阻塞
// 适用于读多写少的情况
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
