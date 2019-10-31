package com.youdushufang.concurrent.queue;

import com.youdushufang.concurrent.utils.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {

    private int capacity;

    private LinkedList<E> elements;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();

    private final Condition notFull = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new LinkedList<>();
    }

    public E poll() throws InterruptedException {
        E element;
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            Log.log("poll");
            while (elements.size() == 0) {
                notEmpty.await();
            }
            element = elements.poll();
            notFull.signal();
            Log.log("polled " + element);
        } finally {
            lock.unlock();
        }
        return element;
    }

    public void add(E element) throws InterruptedException {
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            Log.log("add " + element);
            while (elements.size() >= capacity) {
                notFull.await();
            }
            elements.add(element);
            notEmpty.signal();
            Log.log("added " + element);
        } finally {
            lock.unlock();
        }
    }
}
