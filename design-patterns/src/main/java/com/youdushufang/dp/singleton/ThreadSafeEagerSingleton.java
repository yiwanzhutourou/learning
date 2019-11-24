package com.youdushufang.dp.singleton;

public class ThreadSafeEagerSingleton {

    private static final ThreadSafeEagerSingleton INSTANCE = new ThreadSafeEagerSingleton();

    private ThreadSafeEagerSingleton() { }

    public static ThreadSafeEagerSingleton getInstance() {
        return INSTANCE;
    }
}
