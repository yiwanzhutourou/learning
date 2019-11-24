package com.youdushufang.dp.singleton;

public class ThreadSafeLazySingleton {

    private static ThreadSafeLazySingleton INSTANCE = null;

    private ThreadSafeLazySingleton() { }

    public static synchronized ThreadSafeLazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThreadSafeLazySingleton();
        }
        return INSTANCE;
    }
}
