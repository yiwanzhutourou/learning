package com.youdushufang.dp.singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

public class SingletonTest {

    @Test
    public void testEnumSingletonByReflection() throws NoSuchMethodException {
        Class<EnumSingleton> singletonClass = EnumSingleton.class;
        Constructor<EnumSingleton> constructor = singletonClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        try {
            EnumSingleton singleton1 = constructor.newInstance("INSTANCE", 0);
            EnumSingleton singleton2 = constructor.newInstance("INSTANCE", 0);
            System.out.print(singleton1 == singleton2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingletonByReflection() throws NoSuchMethodException {
        Class<Singleton> singletonClass = Singleton.class;
        Constructor<Singleton> constructor = singletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            Singleton singleton1 = constructor.newInstance();
            Singleton singleton2 = constructor.newInstance();
            System.out.print(singleton1 == singleton2);
        } catch (Exception ignored) { }
    }

    @Test
    public void testEnumSingletonByJackson() throws JsonProcessingException {
        String json = "\"INSTANCE\"";
        JsonMapper jsonMapper = new JsonMapper();
        EnumSingleton singleton1 = jsonMapper.readValue(json, EnumSingleton.class);
        EnumSingleton singleton2 = jsonMapper.readValue(json, EnumSingleton.class);
        System.out.print(singleton1 == singleton2);
    }

    @Test
    public void testSingletonByJackson() throws JsonProcessingException {
        String json = "{}";
        JsonMapper jsonMapper = new JsonMapper();
        Singleton singleton1 = jsonMapper.readValue(json, Singleton.class);
        Singleton singleton2 = jsonMapper.readValue(json, Singleton.class);
        System.out.print(singleton1 == singleton2);
    }

    @Test
    public void testNotThreadSafeLazySingleton() {
        NotThreadSafeLazySingleton instance1 = NotThreadSafeLazySingleton.getInstance();
        NotThreadSafeLazySingleton instance2 = NotThreadSafeLazySingleton.getInstance();
        Assertions.assertSame(instance1, instance2);
    }

    @Test
    public void testThreadSafeEagerSingleton() throws InterruptedException {
        ThreadSafeEagerSingleton[] instances = new ThreadSafeEagerSingleton[2];
        Thread thread1 = new Thread(() -> instances[0] = ThreadSafeEagerSingleton.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = ThreadSafeEagerSingleton.getInstance());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertSame(instances[0], instances[1]);
    }

    @Test
    public void testThreadSafeLazySingleton() throws InterruptedException {
        ThreadSafeLazySingleton[] instances = new ThreadSafeLazySingleton[2];
        Thread thread1 = new Thread(() -> instances[0] = ThreadSafeLazySingleton.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = ThreadSafeLazySingleton.getInstance());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertSame(instances[0], instances[1]);
    }

    @Test
    public void testDoubleCheckedLockingSingleton() throws InterruptedException {
        DoubleCheckedLockingSingleton[] instances = new DoubleCheckedLockingSingleton[2];
        Thread thread1 = new Thread(() -> instances[0] = DoubleCheckedLockingSingleton.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = DoubleCheckedLockingSingleton.getInstance());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertSame(instances[0], instances[1]);
    }

    @Test
    public void testInnerClassSingleton() throws InterruptedException {
        System.out.println(InnerClassSingleton.NAME);
        InnerClassSingleton[] instances = new InnerClassSingleton[2];
        Thread thread1 = new Thread(() -> instances[0] = InnerClassSingleton.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = InnerClassSingleton.getInstance());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertSame(instances[0], instances[1]);
    }

    @Test
    public void testEnumSingleton() throws InterruptedException {
        EnumSingleton[] instances = new EnumSingleton[2];
        Thread thread1 = new Thread(() -> instances[0] = EnumSingleton.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = EnumSingleton.getInstance());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assertions.assertSame(instances[0], instances[1]);
    }
}
