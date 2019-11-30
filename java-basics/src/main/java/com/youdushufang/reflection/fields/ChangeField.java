package com.youdushufang.reflection.fields;

import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.System.out;

public class ChangeField {

    public static void main(String[] args) {
        Book book = new Book();
        String fmt = "%6S:  %-12s = %s%n";

        Class<?> c = book.getClass();

        Field chap;
        try {
            chap = c.getDeclaredField("chapters");
            out.format(fmt, "before", "chapters", book.chapters);
            chap.setLong(book, 12);
            out.format(fmt, "after", "chapters", chap.getLong(book));

            // private field
            Field chars = c.getDeclaredField("characters");
            chars.setAccessible(true);
            out.format(fmt, "before", "characters", Arrays.asList((String[]) chars.get(book)));
            String[] newChars = { "Queen", "King" };
            chars.set(book, newChars);
            out.format(fmt, "after", "characters", Arrays.asList((String[]) chars.get(book)));

            // final field
            Field t = c.getDeclaredField("twin");
            out.format(fmt, "before", "twin", book.twin);
            t.setAccessible(true);
            t.set(book, Tweedle.DUM);
            out.format(fmt, "after", "twin", book.twin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
