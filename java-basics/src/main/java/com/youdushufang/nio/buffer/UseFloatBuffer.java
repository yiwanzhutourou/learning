package com.youdushufang.nio.buffer;

import java.nio.FloatBuffer;

public class UseFloatBuffer {

    public static void main(String[] args) {
        FloatBuffer floatBuffer = FloatBuffer.allocate(10);
        for (int i = 0; i < floatBuffer.capacity(); i++) {
            floatBuffer.put((float) Math.sin((i / 10f) * Math.PI * 2));
        }
        floatBuffer.flip();
        while (floatBuffer.hasRemaining()) {
            System.out.println(floatBuffer.get());
        }
    }
}
