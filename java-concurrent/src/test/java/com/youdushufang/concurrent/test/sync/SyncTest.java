package com.youdushufang.concurrent.test.sync;

import com.youdushufang.concurrent.sync.SyncHttpClient;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

class SyncTest {

    @Test
    void testGet() {
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
