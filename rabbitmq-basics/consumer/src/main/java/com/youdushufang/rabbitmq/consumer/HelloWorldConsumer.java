package com.youdushufang.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class HelloWorldConsumer {

    private static final String QUEUE_NAME = "hello_world";

    private static volatile HelloWorldConsumer instance;

    private HelloWorldConsumer() { }

    static HelloWorldConsumer getInstance() {
        if (instance == null) {
            synchronized (HelloWorldConsumer.class) {
                if (instance == null) {
                    instance = new HelloWorldConsumer();
                }
            }
        }
        return instance;
    }

    void consume() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,
                false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME,
                true, deliverCallback, consumerTag -> { });
    }
}
