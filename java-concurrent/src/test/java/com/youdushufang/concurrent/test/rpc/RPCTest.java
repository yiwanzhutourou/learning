package com.youdushufang.concurrent.test.rpc;

import com.youdushufang.concurrent.mq.MessageQueue;
import com.youdushufang.concurrent.rpc.RPCClient;
import com.youdushufang.concurrent.rpc.RPCServer;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class RPCTest {

    @Test
    void test() {
        // 模拟两个消息队列
        MessageQueue requestQueue = MessageQueue.newMessageQueue("request-queue");
        MessageQueue replyQueue = MessageQueue.newMessageQueue("reply-queue");

        // 启动一个模拟的 RPC server
        RPCServer rpcServer = new RPCServer(requestQueue, replyQueue);
        rpcServer.serve();

        RPCClient rpcClient = new RPCClient(requestQueue, replyQueue);
        Future<String> futureResult = rpcClient.call("Hello, World");
        try {
            Log.log(futureResult.get(1, TimeUnit.SECONDS));
            Log.log(futureResult.get());
            Log.log(futureResult.get(1, TimeUnit.SECONDS));
            Log.log(futureResult.get());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rpcServer.shutdown();
            replyQueue.shutdown();
            requestQueue.shutdown();
        }
    }
}
