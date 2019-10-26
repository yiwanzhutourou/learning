package com.youdushufang.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class LogConsumer {

    private static final String EXCHANGE_NAME = "log";

    private static volatile LogConsumer instance;

    private LogConsumer() { }

    static LogConsumer getInstance() {
        if (instance == null) {
            synchronized (LogConsumer.class) {
                if (instance == null) {
                    instance = new LogConsumer();
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

        // 申明一个 fanout exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 申明一个服务命名的，自动删除的，非持久化的 queue，获取 queue 的名字
        // 这种 queue 适用于 log 这个场景，每次服务启动，我需要一个全新的 queue
        // 绑定到 log exchange 开始监听 log 即可，服务停止后自动删除这个 queue，并且无需持久化
        String queueName = channel.queueDeclare().getQueue();
        // 将 queue 绑定到 exchange
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
