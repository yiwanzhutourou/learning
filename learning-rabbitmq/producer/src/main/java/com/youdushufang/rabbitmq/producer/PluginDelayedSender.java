package com.youdushufang.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

class PluginDelayedSender {

    private static final String EXCHANGE_NAME = "plugin_delayed_exchange";

    private static volatile PluginDelayedSender instance;

    private PluginDelayedSender() { }

    static PluginDelayedSender getInstance() {
        if (instance == null) {
            synchronized (PluginDelayedSender.class) {
                if (instance == null) {
                    instance = new PluginDelayedSender();
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
            // 申明一个 delayed exchange
            Map<String, Object> args = new HashMap<>();
            args.put("x-delayed-type", "direct");
            channel.exchangeDeclare(EXCHANGE_NAME,
                    "x-delayed-message", true, false, args);

            // 发送一个 10 秒延迟的消息
            String message1 = "Delayed payload in 10 seconds";
            Map<String, Object> headers1 = new HashMap<>();
            headers1.put("x-delay", 10000);
            AMQP.BasicProperties props1 = new AMQP.BasicProperties.Builder()
                    .headers(headers1)
                    .build();
            channel.basicPublish(EXCHANGE_NAME, "", props1, message1.getBytes());
            System.out.println(" [x] Sent '" + message1 + "'");

            // 发送一个 5 秒延迟的消息
            String message2 = "Delayed payload in 5 seconds";
            Map<String, Object> headers2 = new HashMap<>();
            headers2.put("x-delay", 5000);
            AMQP.BasicProperties props2 = new AMQP.BasicProperties.Builder()
                    .headers(headers2)
                    .build();
            channel.basicPublish(EXCHANGE_NAME, "", props2, message2.getBytes());
            System.out.println(" [x] Sent '" + message2 + "'");
        }
    }
}
