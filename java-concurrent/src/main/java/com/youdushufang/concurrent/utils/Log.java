package com.youdushufang.concurrent.utils;

import com.youdushufang.concurrent.local.SafeDateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Log {

    private static final ThreadLocal<SimpleDateFormat> tl = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    );

    public static void log(Object object) {
        log(Objects.toString(object));
    }

    public static void log(String message) {
        System.out.println(" [ x ] [" + Thread.currentThread().getName()
                + "] [" + tl.get().format(new Date()) + "] " + message);
    }
}
