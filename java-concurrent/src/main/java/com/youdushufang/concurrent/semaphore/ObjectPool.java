package com.youdushufang.concurrent.semaphore;

import com.youdushufang.concurrent.utils.Log;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 对象池，保证同时只能有一个线程访问同一个对象
 */
public class ObjectPool<T, R> {

    private final Vector<T> objects;

    private final Semaphore semaphore;

    public ObjectPool(Collection<T> objects) {
        this.objects = new Vector<>(objects);
        this.semaphore = new Semaphore(objects.size());
    }

    // 对外只暴露一定的访问接口，避免将对象直接暴露出去，因为不能确定调用方是否会把对象丢到别的线程去用
    public R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        Log.log("exec enter...");
        semaphore.acquire();
        try {
            t = objects.remove(0);
            R result = func.apply(t);
            Log.log("exec exit, using obj = " + t);
            return result;
        } finally {
            if (t != null) {
                objects.add(t);
            }
            semaphore.release();
        }
    }
}
