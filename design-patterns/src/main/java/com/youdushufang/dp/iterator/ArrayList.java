package com.youdushufang.dp.iterator;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {

    private static final int INIT_ARRAY_SIZE = 8;

    private Object[] elements;

    private int size;

    public ArrayList() {
        this(INIT_ARRAY_SIZE);
    }

    public ArrayList(int initialSize) {
        this.elements = new Object[initialSize];
        this.size = 0;
    }

    @Override
    public void add(E e) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = e;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) elements[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private void grow() {
        // 扩容一倍
        int newSize = this.elements.length << 1;
        // 如果大小已经溢出了，容量增加 1
        if (newSize < 0) {
            newSize = this.elements.length + 1;
        }
        // 仍然溢出了，不能再增加新元素了
        if (newSize < 0) {
            throw new OutOfMemoryError();
        }
        this.elements = Arrays.copyOf(this.elements, newSize);
    }

    private class ArrayListIterator implements Iterator<E> {

        private int cursor;

        ArrayListIterator() {
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (cursor >= size || cursor >= elements.length) {
                throw new IndexOutOfBoundsException();
            }
            return (E) elements[cursor++];
        }
    }
}
