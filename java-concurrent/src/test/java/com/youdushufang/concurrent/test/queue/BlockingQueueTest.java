package com.youdushufang.concurrent.test.queue;

import com.youdushufang.concurrent.queue.BlockingQueue;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

class BlockingQueueTest {

    @Test
    void testPollAndAdd() throws InterruptedException {
        final BlockingQueue<String> blockingQueue = new BlockingQueue<>(3);
        Thread[] addThreads = new Thread[5];
        for (int i = 0; i < addThreads.length; i++) {
            final int threadNum = i;
            addThreads[i] = new Thread(() -> {
                try {
                    String element = "element" + threadNum;
                    blockingQueue.add(element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            addThreads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            Log.log(blockingQueue.poll());
        }
    }

    @Test
    void testSync() throws InterruptedException {
        final BlockingQueue<String> blockingQueue = new BlockingQueue<>(1);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                blockingQueue.add("Hello, World");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Log.log(blockingQueue.poll());
    }
}
