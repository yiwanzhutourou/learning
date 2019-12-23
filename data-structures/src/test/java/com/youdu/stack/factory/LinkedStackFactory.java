package com.youdu.stack.factory;

import com.youdu.stack.LinkedStack;
import com.youdu.stack.Stack;

public class LinkedStackFactory implements StackFactory {

    @Override
    public <E> Stack<E> newStack(int initialSize) {
        return new LinkedStack<>();
    }
}
