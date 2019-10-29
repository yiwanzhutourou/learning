package com.youdushufang.concurrent.mq;

import com.youdushufang.concurrent.utils.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 本地模拟一个消息队列
 */
public class MessageQueue {

    private final BlockingQueue<String> messages;

    private final List<DeliverCallback> consumers;

    private volatile boolean isShutDown = false;

    private Thread mainThread;

    private MessageQueue(String name) {
        this.messages = new LinkedBlockingQueue<>();
        this.consumers = new LinkedList<>();

        // 起一个线程不断消耗消息
        MainLooper mainLooper = new MainLooper();
        mainThread = new Thread(mainLooper);
        mainThread.setName("MessageQueue: " + name);
        mainThread.start();
    }

    public static MessageQueue newMessageQueue(String name) {
        return new MessageQueue(name);
    }

    public void shutdown() {
        isShutDown = true;
        mainThread.interrupt();
    }

    public void publish(String message) {
        if (isShutDown) {
            throw new IllegalStateException("Message Queue is shut down");
        }
        messages.offer(message);
    }

    public synchronized void consume(DeliverCallback callback) {
        if (callback != null) {
            consumers.add(callback);
        }
    }

    private class MainLooper implements Runnable {

        @Override
        public void run() {
            while (!isShutDown) {
                try {
                    // 如果当前没有订阅者消息也直接消耗掉
                    String message = messages.take();
                    if (consumers.size() > 0) {
                        for (DeliverCallback deliverCallback : consumers) {
                            deliverCallback.handle(message);
                        }
                    }
                } catch (InterruptedException e) {
                    if (!isShutDown) {
                        Log.log(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
