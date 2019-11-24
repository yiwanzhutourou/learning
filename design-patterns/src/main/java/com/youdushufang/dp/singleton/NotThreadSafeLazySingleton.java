package com.youdushufang.dp.singleton;

public class NotThreadSafeLazySingleton {

    private static NotThreadSafeLazySingleton INSTANCE = null;

    private NotThreadSafeLazySingleton() {
        System.out.println("NotThreadSafeLazySingleton constructor called");
    }

    public static NotThreadSafeLazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotThreadSafeLazySingleton();
        }
        return INSTANCE;
    }
}
