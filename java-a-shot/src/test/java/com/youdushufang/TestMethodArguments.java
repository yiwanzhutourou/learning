package com.youdushufang;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TestMethodArguments {

    @Test
    void testValueOrReference() {
        String string = "an old man";
        StringBuilder stringBuilder = new StringBuilder("i need ");
        stupidMethod(string, stringBuilder);
        System.out.println(string);
        System.out.println(stringBuilder);
    }

    @Test
    void testMultiArgs() {
        System.out.println(count(new int[] {1, 2, 3}));
        System.out.println(count(new Integer[] {1, 2, 3}));
        System.out.println(count(1, new Integer[] {1, 2, 3}));

        System.out.println(Arrays.asList(new int[] {1, 2, 3}));
        System.out.println(Arrays.asList(new Integer[] {1, 2, 3}));
        System.out.println(Arrays.asList(1, new Integer[] {1, 2, 3}));
    }

    @SafeVarargs
    private static <T> int count(T... ts) {
        return ts.length;
    }

    private void stupidMethod(String string, StringBuilder stringBuilder) {
        string = "a new man";
        stringBuilder.append("fresh air");
        stringBuilder = new StringBuilder();
        stringBuilder.append("and some food");
    }
}
