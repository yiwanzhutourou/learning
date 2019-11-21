package com.youdushufang.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

class PluginDelayedConsumer {

    private static final String EXCHANGE_NAME = "plugin_delayed_exchange";

    private static final String QUEUE_NAME = "plugin_delayed_queue";

    private static volatile PluginDelayedConsumer instance;

    private PluginDelayedConsumer() { }

    static PluginDelayedConsumer getInstance() {
        if (instance == null) {
            synchronized (PluginDelayedConsumer.class) {
                if (instance == null) {
                    instance = new PluginDelayedConsumer();
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

        // 申明一个 delayed exchange
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        channel.exchangeDeclare(EXCHANGE_NAME,
                "x-delayed-message", true, false, args);

        channel.queueDeclare(QUEUE_NAME,
                true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
