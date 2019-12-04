package com.youdushufang.nio.buffer;

import java.nio.ByteBuffer;

public class TypesInByteBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(30);
        byteBuffer.putLong(Long.MAX_VALUE);
        byteBuffer.putDouble(Math.PI);
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
    }
}
