package com.youdu.stack.factory;

import com.youdu.stack.ArrayStack;
import com.youdu.stack.Stack;

public class ArrayStackFactory implements StackFactory {

    @Override
    public <E> Stack<E> newStack(int initialSize) {
        return new ArrayStack<>(initialSize);
    }
}
