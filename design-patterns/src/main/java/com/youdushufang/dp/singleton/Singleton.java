package com.youdushufang.dp.singleton;

public class Singleton {

    private static volatile Singleton INSTANCE = null;

    private Singleton() {
        System.out.println("Singleton constructor called");
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
