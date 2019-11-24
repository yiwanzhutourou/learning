package com.youdushufang.test.io;

import com.youdushufang.io.CopyBytes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CopyBytesTest {

    @Test
    void testCopyFileBytes() {
        try {
            CopyBytes.copyFileBytes("io_files/xanadu.txt", "io_files/outagain.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCopyFileBytesBuffered() {
        try {
            CopyBytes.copyFileBytesBuffered("io_files/xanadu.txt", "io_files/outagain.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCopyFileBytesUsingChannel() {
        try {
            CopyBytes.copyFileBytesUsingChannel("io_files/xanadu.txt", "io_files/outagain.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
