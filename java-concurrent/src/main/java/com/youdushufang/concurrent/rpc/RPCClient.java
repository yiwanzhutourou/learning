package com.youdushufang.concurrent.rpc;

import com.youdushufang.concurrent.mq.MessageQueue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用消息队列实现的异步 RPC Client
 */
public class RPCClient {

    private final MessageQueue requestQueue;

    private final Map<String, Callback> callers;

    public RPCClient(MessageQueue requestQueue, MessageQueue replyQueue) {
        this.requestQueue = requestQueue;
        this.callers = new ConcurrentHashMap<>();
        // 监听 reply queue
        replyQueue.consume(this::handleResult);
    }

    public void call(String method, Callback callback) {
        // 简化问题，直接拿 method 做 key
        callers.put(method, callback);
        // 向 request queue 丢一条消息
        requestQueue.publish(method);
    }

    public String callSync(String method) throws InterruptedException {
        final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
        callers.put(method, blockingQueue::offer);
        requestQueue.publish(method);
        return blockingQueue.take();
    }

    private void handleResult(String message) {
        Callback callback = callers.remove(message);
        if (callback != null) {
            callback.handle(message);
        }
    }
}
