package com.youdushufang.concurrent.rpc;

import com.youdushufang.concurrent.mq.MessageQueue;
import com.youdushufang.concurrent.utils.JsonMapper;
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

    private final JsonMapper jsonMapper;

    private final Random random = new Random();

    private volatile boolean isShutDown = false;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public RPCServer(MessageQueue requestQueue, MessageQueue replyQueue) {
        this.requestQueue = requestQueue;
        this.replyQueue = replyQueue;
        this.jsonMapper = new JsonMapper();
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

        private final String message;

        Worker(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            if (isShutDown) {
                return;
            }
            Log.log("server worker start with " + message);
            Request request = jsonMapper.fromJson(message, Request.class);
            if (request == null) {
                return;
            }
            // 处理请求
            Response response = handleRequest(request);
            replyQueue.publish(jsonMapper.toJson(response));
        }
    }

    private Response handleRequest(Request request) {
        // 随机睡 1 - 5 秒
        int sleepTime = random.nextInt(4) + 1;
        try {
            Thread.sleep(sleepTime * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.log("server worker end with " + request + ", work time = " + sleepTime);
        return Response.newResponse(request.getId(), request.getBody());
    }
}
