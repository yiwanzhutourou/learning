package com.youdushufang.util;

public class StringUtils {

    private StringUtils() { }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }
}
