package com.youdushufang.concurrent.test.local;

import com.youdushufang.concurrent.local.SafeDateFormat;
import com.youdushufang.concurrent.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ThreadLocalTest {

    @Test
    void testSafeDateFormatExample() throws InterruptedException {
        Thread thread1 = new Thread(() -> Log.log(SafeDateFormat.format(new Date())));
        Thread thread2 = new Thread(() -> Log.log(SafeDateFormat.format(new Date())));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
