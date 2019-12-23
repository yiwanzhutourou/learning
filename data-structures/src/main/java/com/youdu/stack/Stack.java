package com.youdu.stack;

public interface Stack<E> {

    boolean push(E element);

    E pop();

    E peak();

    int size();
}
