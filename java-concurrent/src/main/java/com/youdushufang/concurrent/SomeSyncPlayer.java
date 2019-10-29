package com.youdushufang.concurrent;

import com.youdushufang.concurrent.sync.SyncHttpClient;
import com.youdushufang.concurrent.utils.Log;

import java.util.concurrent.Executors;

class SomeSyncPlayer {

    private SomeSyncPlayer() { }

    static void play() {
        SyncHttpClient syncHttpClient = new SyncHttpClient(Executors.newSingleThreadExecutor());
        try {
            Log.log(syncHttpClient.get0("Hello, CountDownLatch"));
            Log.log(syncHttpClient.get1("Hello, BlockingQueue"));
            Log.log(syncHttpClient.get2("Hello, wait"));
            Log.log(syncHttpClient.get3("Hello, ReentrantLock"));
            Log.log(syncHttpClient.get4("Hello, Flux"));
            Log.log(syncHttpClient.get5("Hello, RxJava"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            syncHttpClient.shutdown();
        }
    }
}
