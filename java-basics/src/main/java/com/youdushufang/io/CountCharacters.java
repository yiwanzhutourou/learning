package com.youdushufang.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class CountCharacters {

    private CountCharacters() {}

    public static long countCharacters(Path file, char c) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        Charset charset = StandardCharsets.UTF_8;
        long count = 0;
        try (FileChannel fileChannel = FileChannel.open(file)) {
            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                CharBuffer charBuffer = charset.decode(buffer);
                long bufferCount = count(charBuffer, c);
                System.out.format("%s contains %d '%s'%n", charBuffer, bufferCount, c);
                count += bufferCount;
                buffer.clear();
            }
        }
        return count;
    }

    private static long count(CharSequence text, char c) {
        if (text != null && text.length() > 0) {
            return text.chars()
                    .filter(t -> (t == c))
                    .count();
        }
        return 0L;
    }
}
