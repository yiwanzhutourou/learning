package com.youdu.stack.factory;

import com.youdu.stack.Stack;

public interface StackFactory {

    <E> Stack<E> newStack(int initialSize);
}
