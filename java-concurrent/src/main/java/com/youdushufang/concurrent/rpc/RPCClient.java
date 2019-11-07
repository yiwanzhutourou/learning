package com.youdushufang.concurrent.rpc;

import com.youdushufang.concurrent.mq.MessageQueue;
import com.youdushufang.concurrent.utils.JsonMapper;
import com.youdushufang.concurrent.utils.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用消息队列实现的异步 RPC Client
 */
public class RPCClient {

    // 模拟消息 id
    private static final AtomicInteger accumulator = new AtomicInteger(1);

    private final MessageQueue requestQueue;

    private final JsonMapper jsonMapper;

    private static Map<Integer, DefaultFuture> futures = new ConcurrentHashMap<>();

    public RPCClient(MessageQueue requestQueue, MessageQueue replyQueue) {
        this.requestQueue = requestQueue;
        this.jsonMapper = new JsonMapper();
        // 监听 reply queue
        replyQueue.consume(this::handleResult);
    }

    // ISSUE: 没有提供超时机制，如果调用失败这个 Future 对象就不能被正确移除了
    public Future<String> call(String body) {
        Request request = Request.newRequest(accumulator.getAndIncrement(), body);
        DefaultFuture future = new DefaultFuture(request.getId());
        futures.put(request.getId(), future);
        requestQueue.publish(jsonMapper.toJson(request));
        return future;
    }

    private void handleResult(String message) {
        Response response = jsonMapper.fromJson(message, Response.class);
        if (response == null) {
            Log.log("[warning] null response get, message = " + message);
            return;
        }
        DefaultFuture future = futures.remove(response.getRequestId());
        if (future == null) {
            Log.log("[warning] null future get, message = " + message);
            return;
        }
        try {
            future.onChanged(response.getResult());
        } catch (InterruptedException e) {
            Log.log("thread interrupted, message = " + message);
            e.printStackTrace();
        }
    }

    private static class DefaultFuture implements Future<String> {

        private Integer id;

        private String result;

        private volatile boolean canceled = false;

        private final Lock lock = new ReentrantLock();

        private final Condition done = lock.newCondition();

        DefaultFuture(Integer id) {
            this.id = id;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            this.canceled = true;
            if (mayInterruptIfRunning) {
                futures.remove(id);
                try {
                    onChanged(null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean isCancelled() {
            return canceled;
        }

        @Override
        public boolean isDone() {
            return result != null;
        }

        @Override
        public String get() throws InterruptedException {
            lock.lockInterruptibly();
            try {
                while (result == null) {
                    done.await();
                }
            } finally {
                lock.unlock();
            }
            return result;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException {
            lock.lockInterruptibly();
            try {
                // 超时就不等了
                if (result == null) {
                    done.await(timeout, unit);
                }
            } finally {
                lock.unlock();
            }
            return result;
        }

        private void onChanged(String result) throws InterruptedException {
            lock.lockInterruptibly();
            try {
                this.result = result;
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
