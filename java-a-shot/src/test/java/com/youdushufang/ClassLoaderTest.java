package com.youdushufang;

import com.youdushufang.jvm.class_loader.SubClass;
import com.youdushufang.jvm.class_loader.SuperClass;
import org.junit.jupiter.api.Test;

class ClassLoaderTest {

    @Test
    void testSubClassInit() {
        // 以下语句不会触发 SubClass 的初始化
        // 是否会触发 SubClass 的加载可以通过增加启动参数 -XX:+TraceClassLoading 观察
        System.out.println(SubClass.value);
    }

    @Test
    void testSuperClassInit() {
        // 数组定义不会触发 SuperClass 类的初始化
        System.out.println(new SuperClass[4].length);
    }

    @Test
    void testConstant() {
        // 以下代码不会不触发 SuperClass 的初始化
        // 使用启动参数 -XX:+TraceClassLoading 观察发现 SuperClass 类根本就没被加载
        // 常量 SuperClass.HELLO_WORLD 在编译期其实已经保存到 ClassLoaderTest 类的常量池里了，
        // 执行时 ClassLoaderTest 引用的其实是自身常量池里的常量
        System.out.println(SuperClass.HELLO_WORLD);
    }
}
