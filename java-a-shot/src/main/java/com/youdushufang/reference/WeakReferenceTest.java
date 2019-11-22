package com.youdushufang.reference;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

    public static void main(String[] args) {
        Foo foo = new Foo();
        WeakReference<Foo> weakReference = new WeakReference<>(foo);
        // 释放强引用，这样只有弱引用指向刚刚创建的对象
        foo = null;
        System.out.println(foo);
        while (true) {
            // 提示 JVM 进行 gc，可以增加启动参数 -XX:+PrintGCDetails 查看
            System.gc();
            System.runFinalization();
            // 一次 GC 后，如果对象只有弱引用指向，那么就会被回收
            // 如果对象仍然有强引用指向，则不会被回收，弱引用这是还可以通过 get() 获取
            if (weakReference.get() == null) {
                System.out.println("collected");
                break;
            } else {
                System.out.println("still there");
            }
        }
    }

    private static class Foo { }
}
