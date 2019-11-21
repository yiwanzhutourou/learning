package com.youdushufang.test.io;

import com.youdushufang.io.CopyCharacters;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CopyCharactersTest {

    @Test
    void testCopyFileChars() {
        try {
            CopyCharacters.copyFileChars("io_files/xanadu.txt", "io_files/outagain.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCopyFileLines() {
        try {
            CopyCharacters.copyFileLines("io_files/xanadu.txt", "io_files/outagain.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
