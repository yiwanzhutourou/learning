package com.youdushufang.stupid.jvm.class_loader;

public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}
