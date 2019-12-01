package com.youdushufang.reflection.arrays;

import java.awt.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import static java.lang.System.out;

public class ArrayFinder {

    public static void main(String[] args) {
        findArray(ByteBuffer.class);
        findArray(Cursor.class);
    }

    private static void findArray(Class<?> cls) {
        boolean found = false;
        Field[] flds = cls.getDeclaredFields();
        for (Field f : flds) {
            Class<?> c = f.getType();
            if (c.isArray()) {
                found = true;
                out.format("%s%n"
                                + "           Field: %s%n"
                                + "            Type: %s%n"
                                + "  Component Type: %s%n",
                        f, f.getName(), c, c.getComponentType());
            }
        }
        if (!found) {
            out.format("No array fields%n");
        }
    }
}
