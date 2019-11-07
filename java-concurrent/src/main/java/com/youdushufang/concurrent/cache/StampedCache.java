package com.youdushufang.concurrent.cache;

import com.youdushufang.concurrent.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

// StampedLock 支持三种模式，分别是：写锁、悲观读锁和乐观读。
// 其中，写锁、悲观读锁的语义和 ReadWriteLock 的写锁、读锁的语义非常类似，
// 允许多个线程同时获取悲观读锁，但是只允许一个线程获取写锁，写锁和悲观读锁是互斥的。
// 不同的是：StampedLock 里的写锁和悲观读锁加锁成功之后，都会返回一个 stamp；
// 然后解锁的时候，需要传入这个 stamp。
// StampedLock 不支持重入
// StampedLock 的悲观读锁、写锁都不支持条件变量

// 根据实验代码，看起来似乎是写锁释放后才算数据有变动，由于乐观读是无锁的，因此存在读到脏数据的情况
// 我理解的是，乐观读用在缓存上是要看应用场景的，由于是无锁的，可以提高读的效率，某些场景下可以允许存在一定的脏读，
// 而在涉及到某些关键逻辑时再保证数据的一致性
public class StampedCache<K, V> {

    private final Map<K, V> m = new HashMap<>();

    private final StampedLock stampedLock = new StampedLock();

    public void put(K key, V value) throws InterruptedException {
        // 写锁
        long stamp = stampedLock.writeLockInterruptibly();
        Log.log("enter write lock...");
        try {
            m.put(key, value);
        } finally {
            Log.log("ready to exit write lock...");
            stampedLock.unlockWrite(stamp);
        }
    }

    // 悲观读
    public V get(K key) throws InterruptedException {
        // 先尝试乐观读，这个操作是无锁的
        long stamp = stampedLock.readLockInterruptibly();
        try {
            return m.get(key);
        } finally {
            // 释放悲观读锁
            stampedLock.unlockRead(stamp);
        }
    }

    public V optimisticGet(K key) throws InterruptedException {
        // 先尝试乐观读，这个操作是无锁的
        long stamp = stampedLock.tryOptimisticRead();
        V value = m.get(key);
        // 判断是否存在写操作
        if (!stampedLock.validate(stamp)) {
            // 如果存在，升级为悲观读锁
            stamp = stampedLock.readLockInterruptibly();
            try {
                value = m.get(key);
            } finally {
                // 释放悲观锁
                stampedLock.unlockRead(stamp);
            }
        }
        return value;
    }
}
