package com.youdushufang.generic;

public class Wrapper<T> {

    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }

    // PECS: producer extends consumer super
    public static <V> void copy(Wrapper<? extends V> from, Wrapper<? super V> to) {
        to.set(from.get());
    }
}
