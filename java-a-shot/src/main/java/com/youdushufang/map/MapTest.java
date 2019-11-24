package com.youdushufang.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟 JDK 7 HashMap 多线程并发读写时发生的"死链现象"
 *
 * HashMap 在调用 put 函数时可能触发 resize，resize 时要对目前所有元素重新
 * 计算 hash 并迁移数据，transfer() 方法模拟这个过程，transfer() 方法实现的
 * 数据迁移在并发情况下可能出现 a -> b -> a 这样循环链接的情况，导致 print()
 * 方法死循环。
 *
 * 注：运行以下代码可能出现进程 CPU 使用率飙升的情况
 */
public class MapTest {

    private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

    private static Node root;

    public static void main(String[] args) throws InterruptedException {
        // 构造一个两个节点的链表
        Node a = new Node(1);
        Node b = new Node(2);
        root = a;
        a.next = b;
        print();

        // 构造一堆线程并发地地调用 transfer() 和 print()
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    transfer();
                    print();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    // 线程不安全的读操作
    private static void print() {
        Node n = root;
        System.out.println(" [x] " + Thread.currentThread().getName());
        // 整一个对象出来，循环多了最后会 OOM，防止 CPU 被占满
        while (n != null) {
            System.out.print(n.value + " -> ");
            n = n.next;
        }
        System.out.println("null");
    }

    // 线程不安全的写操作
    // 模拟数据迁移，将老链表拷贝到新链表
    private static void transfer() {
        Node n = root;
        Node newRoot = null;
        while (n != null) {
            Node next = n.next;
            n.next = newRoot;
            newRoot = n;
            n = next;
        }
        root = newRoot;
    }

    private static class Node {
        private int value;
        private Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
