package com.youdushufang.test.io;

import com.youdushufang.io.WatchDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class WatchDirectoryTest {

    @Test
    void testWatchingDirectory() throws IOException {
        Path dir = Paths.get("io_files");
        new WatchDirectory(dir, false).processEvents();
    }
}
