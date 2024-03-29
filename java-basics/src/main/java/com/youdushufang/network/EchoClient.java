package com.youdushufang.network;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
        for (int i = 0; i < 10000; i++) {
            executor.submit(() -> requestServer("Hello, from " + Thread.currentThread().getName()));
        }
        TimeUnit.SECONDS.sleep(10);
        executor.shutdown();
    }

    private static void requestServer(String message) {
        try (Socket socket = new Socket("localhost", 5566);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {
            out.println(message);
            log.info("echo: " + in.readLine());
        } catch (IOException e) {
            log.error("error: " + e);
            System.exit(1);
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
