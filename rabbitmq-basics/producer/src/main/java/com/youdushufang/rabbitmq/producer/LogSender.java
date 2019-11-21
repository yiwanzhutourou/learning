package com.youdushufang.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

class LogSender {

    private static final String EXCHANGE_NAME = "log";

    private static volatile LogSender instance;

    private final Random random = new Random();

    private LogSender() { }

    static LogSender getInstance() {
        if (instance == null) {
            synchronized (LogSender.class) {
                if (instance == null) {
                    instance = new LogSender();
                }
            }
        }
        return instance;
    }

    void send() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 申明一个 fanout exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "Something is happened: " + random.nextInt(1000);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
