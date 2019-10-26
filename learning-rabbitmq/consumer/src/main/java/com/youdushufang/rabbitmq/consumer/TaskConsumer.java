package com.youdushufang.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

class TaskConsumer {

    private static final String QUEUE_NAME = "task_queue";

    private static volatile TaskConsumer instance;

    private Executor executor = Executors.newFixedThreadPool(4);

    private TaskConsumer() { }

    static TaskConsumer getInstance() {
        if (instance == null) {
            synchronized (TaskConsumer.class) {
                if (instance == null) {
                    instance = new TaskConsumer();
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

        // durable = true
        channel.queueDeclare(QUEUE_NAME,
                true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Consumer 同时只接受一条消息，该 Consumer 未处理完某条消息（未发送 ack 确认消息）时不要再发新的消息过来
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // 主线程只负责接受消息，可以看到消息未处理完前不会收到新的消息
            doWork(delivery, channel);
        };

        // autoAck = false 需要 Consumer 确认消息以收到 RabbitMQ 服务才认为消息被正确处理了
        channel.basicConsume(QUEUE_NAME,
                false, deliverCallback, consumerTag -> { });
    }

    // 用 Thread.sleep 模拟耗时操作
    private void doWork(final Delivery delivery, final Channel channel) {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
        executor.execute(() -> {
            System.out.println(" [x] Doing task in " + Thread.currentThread().getName());
            try {
                for (char ch : message.toCharArray()) {
                    if (ch == '.') {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                System.out.println(" [x] Done");
                // 发送 ack 确认消息，告诉 RabbitMQ 服务这条消息我成功消费完了
                try {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
