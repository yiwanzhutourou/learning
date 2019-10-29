package com.youdushufang.concurrent.sync;

import com.youdushufang.concurrent.utils.Log;

class HttpClient {

    static void get(String url, HttpCallback callback) {
        Log.log("get " + url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.handle(url);
    }
}
