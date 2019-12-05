package com.youdushufang.dp.iterator;

public interface List<E> {

    void add(E e);

    E get(int index);

    int size();

    Iterator<E> iterator();
}
