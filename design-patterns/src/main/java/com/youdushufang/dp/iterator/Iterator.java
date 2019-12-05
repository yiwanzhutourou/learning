package com.youdushufang.dp.iterator;

public interface Iterator<E> {

    boolean hasNext();

    E next();

    default E remove() {
        throw new UnsupportedOperationException("remove");
    }
}
