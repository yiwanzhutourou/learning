package com.youdushufang.concurrent.sync;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SyncHttpClient {

    private final ExecutorService executorService;

    public SyncHttpClient(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public String get0(String url) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<String> results = new ArrayList<>(1);
        executorService.submit(() -> HttpClient.get(url, result -> {
            results.add(result);
            countDownLatch.countDown();
        }));
        // 先判断 results.size()，避免子线程先执行导致死锁
        while (results.size() == 0) {
            countDownLatch.await();
        }
        return results.get(0);
    }

    // 用了 ReentrantLock 做同步
    public String get1(String url) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
        executorService.submit(() -> HttpClient.get(url, blockingQueue::offer));
        return blockingQueue.take();
    }

    public String get2(String url) throws InterruptedException {
        Object lock = new Object();
        List<String> results = new ArrayList<>(1);
        executorService.submit(() -> HttpClient.get(url, result -> {
            results.add(result);
            synchronized (lock) {
                lock.notify();
            }
        }));
        // 先判断 results.size()，避免子线程先执行导致死锁
        while (results.size() == 0) {
            synchronized (lock) {
                lock.wait();
            }
        }
        return results.get(0);
    }

    public String get3(String url) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        List<String> results = new ArrayList<>(1);
        executorService.submit(() -> HttpClient.get(url, result -> {
            results.add(result);
            try {
                lock.lock();
                condition.signal();
            } finally {
                lock.unlock();
            }
        }));

        // 先判断 results.size()，避免子线程先执行导致死锁
        try {
            lock.lock();
            while (results.size() == 0) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        return results.get(0);
    }

    // 用了 CountDownLatch 做同步
    public String get4(String url) {
        return Mono.<String>create(sink -> HttpClient.get(url, sink::success))
                .subscribeOn(reactor.core.scheduler.Schedulers.fromExecutorService(executorService))
                .block();
    }

    // 用了 CountDownLatch 做同步
    public String get5(String url) {
        return Observable.<String>create(emitter -> HttpClient.get(url, message -> {
            emitter.onNext(message);
            emitter.onComplete();
        }))
                .subscribeOn(Schedulers.from(executorService))
                .firstElement()
                .blockingGet();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
