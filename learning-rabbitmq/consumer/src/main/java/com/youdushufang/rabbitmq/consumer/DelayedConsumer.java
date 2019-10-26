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

class DelayedConsumer {

    // producer 实际发送消息的 exchange
    private static final String DELAYED_EXCHANGE_NAME = "delayed_exchange_10_s";

    // 绑定到 delayed_exchange 的队列，没有任何消费者，设置 message-ttl
    private static final String DELAYED_QUEUE_NAME = "delayed_queue_10_s";

    // delayed_queue 设置的 DLX，本身是一个 topic exchange
    private static final String DLX_NAME = "some_topic_exchange";

    // 最终保存消息的 queue，绑定到 DLX
    private static final String REAL_QUEUE_NAME = "some_topic_queue";

    private static volatile DelayedConsumer instance;

    private DelayedConsumer() { }

    static DelayedConsumer getInstance() {
        if (instance == null) {
            synchronized (DelayedConsumer.class) {
                if (instance == null) {
                    instance = new DelayedConsumer();
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

        // 申明一个 direct exchange，该 exchange 唯一绑定了一个设置了 message-ttl 的 queue
        // 这里似乎也可以直接利用 RabbitMQ 服务自带的无名 exchange
        channel.exchangeDeclare(DELAYED_EXCHANGE_NAME, "direct");

        // 申明 DLX 为一个 topic exchange
        channel.exchangeDeclare(DLX_NAME, "topic");

        // 队列的 TTL 可以通过两种方法设置：
        // 1. 设置 policy，message-ttl 属性
        // 2. 申明队列时设置 x-message-ttl 属性
        // 官网文档推荐的是第一种方法，好处是不需要删除对应的队列就可以修改其应用的策略
        // 采用申明的方法申明队列的 TTL，一旦业务需要修改 TTL 的值，就不得不删除原有
        // 队列，且需要修改业务代码（队列不可以重复申明不同的属性）
        // 这里为了方便采用的是第二种方法，直接在代码里申明队列的 TTL 为 10 秒
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", DLX_NAME);
        // 设置 x-dead-letter-routing-key，否则消息进入 DLX 将仍按照消息自身的 routing key 路由
        // 这里感觉有点不灵活
        args.put("x-dead-letter-routing-key", "some.animal.group");

        channel.queueDeclare(DELAYED_QUEUE_NAME,
                false, false, false, args);
        // 将队列绑定到 exchange，routing key 直接用队列的名字
        channel.queueBind(DELAYED_QUEUE_NAME, DELAYED_EXCHANGE_NAME, DELAYED_QUEUE_NAME);

        // 申明实际存放消息的"延时队列"，并绑定到 DLX
        channel.queueDeclare(REAL_QUEUE_NAME,
                true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 一个 topic 为 *.animal.* 的 queue
        channel.queueBind(REAL_QUEUE_NAME, DLX_NAME, "*.animal.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "', time: " + System.currentTimeMillis() / 1000L);
        };

        channel.basicConsume(REAL_QUEUE_NAME,
                true, deliverCallback, consumerTag -> { });
    }
}
