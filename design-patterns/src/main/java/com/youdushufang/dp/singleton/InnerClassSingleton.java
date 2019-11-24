package com.youdushufang.dp.singleton;

public class InnerClassSingleton {

    public static final String NAME = "OuterClass";

    private InnerClassSingleton() {
        System.out.println("InnerClassSingleton constructor called");
    }

    public static InnerClassSingleton getInstance() {
        return InnerClass.INSTANCE;
    }

    private static class InnerClass {
        static {
            System.out.println("InnerClass init");
        }

        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }
}
