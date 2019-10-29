package com.youdushufang.concurrent.utils;

import java.util.Date;
import java.util.Objects;

public class Log {

    public static void log(Object object) {
        log(Objects.toString(object));
    }

    public static void log(String message) {
        System.out.println(" [ x ] [" + Thread.currentThread().getName()
                + "] [" + new Date() + "] " + message);
    }
}
