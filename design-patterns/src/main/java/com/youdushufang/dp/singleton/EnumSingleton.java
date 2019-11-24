package com.youdushufang.dp.singleton;

public enum EnumSingleton {

    INSTANCE;

    EnumSingleton() {
        System.out.println("EnumSingleton constructor called");
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
