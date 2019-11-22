package com.youdushufang.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DirtyDataInThreadLocal {

    private static Logger logger = LoggerFactory.getLogger(DirtyDataInThreadLocal.class);

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Worker("session1"));
        executorService.submit(new Worker("session2"));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdown();
    }

    private static class Worker implements Runnable {

        private String sessionId;

        Worker(String sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public void run() {
            // 模拟因为代码存在逻辑错误在线程入口处忘记设置 ThreadLocal 值
            if (threadLocal.get() == null) {
                threadLocal.set(sessionId);
            }
            logger.info("user session id: " + threadLocal.get());
            // 线程结束时未清理 ThreadLocal 值，由于线程在线程池中可能存在复用的情况，因此
            // ThreadLocal 中存在的还是上一个业务逻辑的值，有可能用户的 session id 这些
            // 敏感信息就被错误的使用到另外一个用户的业务逻辑了
            // threadLocal.remove();
        }
    }
}
