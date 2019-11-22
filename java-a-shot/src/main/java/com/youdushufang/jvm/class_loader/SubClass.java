package com.youdushufang.jvm.class_loader;

public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}
