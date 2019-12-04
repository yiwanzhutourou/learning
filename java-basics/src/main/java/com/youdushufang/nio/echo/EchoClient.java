package com.youdushufang.nio.echo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EchoClient {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8,
                1000L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new EchoClientThreadFactory());
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> requestServer("Hello, from " + Thread.currentThread().getName()));
        }
        TimeUnit.SECONDS.sleep(10);
        executor.shutdown();
    }

    private static void requestServer(String message) {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        String response;
        try (SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("localhost", 5555))) {
            socketChannel.write(buffer);
            buffer.clear();
            socketChannel.read(buffer);
            response = new String(buffer.array()).trim();
            log.info(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EchoClientThreadFactory implements ThreadFactory {

        private static final String PREFIX = "EchoClient";

        private final AtomicInteger nextId = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String name = PREFIX + nextId.getAndIncrement();
            return new Thread(null, r, name, 0 , false);
        }
    }
}
