package com.youdushufang.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

class TaskSender {

    private static final String QUEUE_NAME = "task_queue";

    private static volatile TaskSender instance;

    private final Random random = new Random();

    private TaskSender() { }

    static TaskSender getInstance() {
        if (instance == null) {
            synchronized (TaskSender.class) {
                if (instance == null) {
                    instance = new TaskSender();
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
            // durable = true: 申明一个持久队列，RabbitMQ 服务重启后队列仍将存在
            channel.queueDeclare(QUEUE_NAME,
                    true, false, false, null);
            String message = getTaskMessage();
            channel.basicPublish("", QUEUE_NAME,
                    // 申明为持久化消息
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

    // 与 Consumer 端约定，收到几个点就工作多少秒
    private String getTaskMessage() {
        int timeToWork = random.nextInt(5) + 3;
        StringBuilder stringBuilder = new StringBuilder("Task");
        for (int i = 0; i < timeToWork; i++) {
            stringBuilder.append(".");
        }
        return stringBuilder.toString();
    }
}
