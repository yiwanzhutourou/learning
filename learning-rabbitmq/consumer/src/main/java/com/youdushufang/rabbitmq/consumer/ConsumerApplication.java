package com.youdushufang.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerApplication {

    public static void main(String[] args) {
        try {
            PluginDelayedConsumer.getInstance().consume();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
