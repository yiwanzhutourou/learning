package com.youdushufang.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class HelloWorldSender {

    private static final String QUEUE_NAME = "hello_world";

    private static volatile HelloWorldSender instance;

    private HelloWorldSender() { }

    static HelloWorldSender getInstance() {
        if (instance == null) {
            synchronized (HelloWorldSender.class) {
                if (instance == null) {
                    instance = new HelloWorldSender();
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
            channel.queueDeclare(QUEUE_NAME,
                    false, false, false, null);
            String message = "Hello, World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
