package com.youdushufang.concurrent.rpc;

import com.youdushufang.concurrent.mq.MessageQueue;
import com.youdushufang.concurrent.utils.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 本地模拟的 RPC server
 */
public class RPCServer {

    private final MessageQueue requestQueue;

    private final MessageQueue replyQueue;

    private volatile boolean isShutDown = false;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public RPCServer(MessageQueue requestQueue, MessageQueue replyQueue) {
        this.requestQueue = requestQueue;
        this.replyQueue = replyQueue;
    }

    public void serve() {
        // 监听 request queue 后启动 worker 干活
        requestQueue.consume(message -> executor.submit(new Worker(message)));
    }

    public void shutdown() {
        isShutDown = true;
        executor.shutdown();
    }

    private class Worker implements Runnable {

        private final String method;

        private final Random random = new Random();

        Worker(String method) {
            this.method = method;
        }

        @Override
        public void run() {
            if (isShutDown) {
                return;
            }
            // 随机睡 1 - 5 秒
            Log.log("server worker start with " + method);
            int sleepTime = random.nextInt(4) + 1;
            try {
                Thread.sleep(sleepTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            replyQueue.publish(method);
            Log.log("server worker end with " + method + ", work time = " + sleepTime);
        }
    }
}
