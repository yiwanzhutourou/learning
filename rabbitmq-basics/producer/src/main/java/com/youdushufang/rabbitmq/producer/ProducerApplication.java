package com.youdushufang.rabbitmq.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerApplication {

    public static void main(String[] args) {
        try {
            PluginDelayedSender.getInstance().send();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
