package com.youdushufang.concurrent.mq;

public interface DeliverCallback {

    // 分布式的消息队列这里应该涉及序列化反序列化的，这里模拟的队列都在内存里
    void handle(String message);
}
