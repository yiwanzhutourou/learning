package com.youdushufang.utils;

public class BinaryUtils {

    private BinaryUtils() { }

    public static String toBinaryString(int number) {
        String binaryString = Integer.toBinaryString(number);
        if (number >= 0) {
            binaryString = suffix("00000000000000000000000000000000" + binaryString, 32);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = i * 4;
            stringBuilder.append(binaryString, index, index + 4)
                    .append("-");
        }
        stringBuilder.append(binaryString, 28, 32);
        return stringBuilder.toString();
    }

    private static String suffix(String string, int length) {
        return string.substring(Math.max(0, string.length() - length));
    }
}
