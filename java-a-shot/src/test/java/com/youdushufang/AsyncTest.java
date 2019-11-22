package com.youdushufang;

import org.junit.jupiter.api.Test;

class AsyncTest {

    private final Object lock = new Object();

    // synchronized 是可重入锁
    // 相同线程每重入一次计数器加一，退出一个 synchronized 代码块计数器减一
    // 计数器减为 0 后才会释放锁
    @Test
    void testReentrant() {
        synchronized (lock) {
            System.out.println("lock once");
            synchronized (lock) {
                System.out.println("lock twice");
                synchronized (lock) {
                    System.out.println("final lock");
                }
            }
        }
    }
}
