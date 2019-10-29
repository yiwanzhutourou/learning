package com.youdushufang.concurrent.mq;

public interface DeliverCallback {

    void handle(String message);
}
