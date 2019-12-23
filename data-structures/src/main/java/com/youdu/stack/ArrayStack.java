package com.youdu.stack;

import java.util.Arrays;

public class ArrayStack<E> implements Stack<E> {

    private int size;

    private Object[] elements;

    public ArrayStack(int initialSize) {
        size = 0;
        elements = new Object[initialSize];
    }

    @Override
    public boolean push(E element) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = element;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pop() {
        if (size == 0) {
            return null;
        }
        Object element = elements[--size];
        return (E) element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peak() {
        if (size == 0) {
            return null;
        }
        Object element = elements[size - 1];
        return (E) element;
    }

    @Override
    public int size() {
        return size;
    }

    private void grow() {
        int newSize = elements.length << 1;
        if (newSize < 0) {
            newSize = elements.length + 1;
        }
        if (newSize < 0) {
            throw new OutOfMemoryError();
        }
        elements = Arrays.copyOf(elements, newSize);
    }
}
