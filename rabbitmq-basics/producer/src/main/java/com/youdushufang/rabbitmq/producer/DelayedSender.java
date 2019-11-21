package com.youdushufang.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 利用队列 TTL 和绑定死信队列（dead letter exchange）来实现延迟消息的发送
class DelayedSender {

    // producer 实际发送消息的 exchange
    private static final String DELAYED_EXCHANGE_NAME = "delayed_exchange_10_s";

    // 绑定到 delayed_exchange 的队列，没有任何消费者，设置 message-ttl
    private static final String DELAYED_QUEUE_NAME = "delayed_queue_10_s";

    private static volatile DelayedSender instance;

    private DelayedSender() { }

    static DelayedSender getInstance() {
        if (instance == null) {
            synchronized (HelloWorldSender.class) {
                if (instance == null) {
                    instance = new DelayedSender();
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

            // 为了简化问题，延时队列相关的 exchange、queue 设置都放在 consumer 端了
            // 需要保证 consumer 端先启动

            // 发送第一条消息，将 expires 设置为 5 秒
            String message1 = "Message Delayed 5 seconds!";
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("5000")
                    .build();
            channel.basicPublish(DELAYED_EXCHANGE_NAME, DELAYED_QUEUE_NAME,
                    properties, message1.getBytes());
            System.out.println(" [x] Sent '" + message1 + "'");

            // 发送第二条消息，沿用 queue 上的 message-ttl 设置
            String message2 = "Message Delayed 10 seconds!";
            channel.basicPublish(DELAYED_EXCHANGE_NAME, DELAYED_QUEUE_NAME,
                    null, message2.getBytes());
            System.out.println(" [x] Sent '" + message2 + "'");

            // 发送第三条消息，将 expires 设置为 5 秒
            // 注意，这条消息并不是在第 5 秒被移除的，而是在第二条消息被移除之后
            // 根据官网文档的描述，消息只有到达队列的头部时才有可能被移除变为死信，因此即使
            // 设置了消息的超时时间，也不能保证消息在正确的时间超时
            // 可以说非常不美好
            String message3 = "Message Delayed 5 seconds!";
            channel.basicPublish(DELAYED_EXCHANGE_NAME, DELAYED_QUEUE_NAME,
                    properties, message3.getBytes());
            System.out.println(" [x] Sent '" + message3 + "'");
        }
    }
}
