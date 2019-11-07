package com.youdushufang.concurrent.local;

import com.youdushufang.concurrent.utils.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SafeDateFormat {

    private static final ThreadLocal<SimpleDateFormat> tl = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    public static String format(Date date) {
        DateFormat dateFormat = tl.get();
        Log.log("format object: 0x" + Integer.toHexString(System.identityHashCode(dateFormat)));
        return dateFormat.format(date);
    }
}
