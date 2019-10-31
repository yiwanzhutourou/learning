package com.youdushufang.concurrent.test.semaphore;

import com.youdushufang.concurrent.semaphore.ObjectPool;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ObjectPoolTest {

    @Test
    void test() throws InterruptedException {
        List<Connection> connections = new ArrayList<>();
        connections.add(new Connection());
        connections.add(new Connection());

        ObjectPool<Connection, String> connectionPool = new ObjectPool<>(connections);

        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Log.log("exec: " + connectionPool.exec(Connection::get));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static class Connection {

        final Random random = new Random();

        String get() {
            Log.log("connect...");
            try {
                Thread.sleep(1000 * (random.nextInt(3) + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, " + Thread.currentThread().getName();
        }
    }
}
