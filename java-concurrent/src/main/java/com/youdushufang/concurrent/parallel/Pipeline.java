package com.youdushufang.concurrent.parallel;

import com.youdushufang.concurrent.utils.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pipeline {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void doParallelWork(int count) throws InterruptedException {
        Log.log("start " + count + " works...");
        Map<Integer, String> works = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            final int index = i;
            executorService.submit(() -> {
                works.put(index, doWork(index));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Log.log("works done: " + works);
    }

    public void doParallelWorkByRxJava(int count) {
        Map<Integer, String> works = Observable.range(1, count)
                .flatMap(index -> Observable.<Pair<Integer, String>>create(emitter -> {
                    emitter.onNext(new Pair<>(index, doWork(index)));
                    emitter.onComplete();
                }).subscribeOn(Schedulers.from(executorService)))
                        .toMap(Pair::getKey, Pair::getValue)
                        .blockingGet();
        Log.log("works done: " + works);
    }

    private String doWork(long index) {
        Log.log("start work " + index);
        try {
            Thread.sleep(index * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.log("finish work " + index);
        return "Work" + index;
    }
}
