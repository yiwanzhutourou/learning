package com.youdushufang.test.io;

import com.youdushufang.io.CountCharacters;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

class FileTest {

    @Test
    void testFileExists() {
        Path path = Paths.get("io_files", "xanadu.txt");
        // 下面两个方法在无法检测路径存在性时（例如程序没有权限访问该路径）都返回 false
        // 因此 !Files.exists(path) 和 Files.notExists(path) 不等价
        System.out.format("exists? %b%n", Files.exists(path));
        System.out.format("notExists? %b%n", Files.notExists(path));

        System.out.format("isReadable? %b%n", Files.isReadable(path));
        System.out.format("isWritable? %b%n", Files.isWritable(path));
        System.out.format("isExecutable? %b%n", Files.isExecutable(path));

        System.out.format("isDirectory? %b%n", Files.isDirectory(path));
        System.out.format("isRegularFile? %b%n", Files.isRegularFile(path));

        Path anotherPath = Paths.get("io_files").resolve("xanadu.txt");
        try {
            System.out.format("isSameFile? %b%n", Files.isSameFile(path, anotherPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFileAttributes() throws IOException {
        Path file = Paths.get("io_files", "xanadu.txt");
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        System.out.println("creationTime: " + attr.creationTime());
        System.out.println("lastAccessTime: " + attr.lastAccessTime());
        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

        System.out.println("isDirectory: " + attr.isDirectory());
        System.out.println("isOther: " + attr.isOther());
        System.out.println("isRegularFile: " + attr.isRegularFile());
        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
        System.out.println("size: " + attr.size());
    }

    @Test
    void testFileOwner() throws IOException {
        Path file = Paths.get("io_files", "xanadu.txt");
        System.out.println("file owner: " + Files.getOwner(file));

        UserPrincipal user = file.getFileSystem().getUserPrincipalLookupService()
                .lookupPrincipalByName(Files.getOwner(file).getName());
        Files.setOwner(file, user);
        System.out.println("file owner: " + Files.getOwner(file));
    }

    @Test
    void testReadingFileUsingReadAllBytes() throws IOException {
        Path file = Paths.get("io_files", "xanadu.txt");
        byte[] bytes = Files.readAllBytes(file);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    void testWritingFileUsingWrite() throws IOException {
        Path file = Paths.get("io_files", "xanadu.txt");
        byte[] buf = "Hello, world\n".getBytes(StandardCharsets.UTF_8);
        Files.write(file, buf, StandardOpenOption.APPEND);

        Files.write(file, Arrays.asList("I,", "am your father"),
                StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    @Test
    void testReadingFileUsingBufferedReader() {
        Path file = Paths.get("io_files", "xanadu.txt");
        try (BufferedReader reader = Files.newBufferedReader(
                file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    void testWritingFileUsingBufferedWriter() {
        Path file = Paths.get("io_files", "xanadu.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            String s = "I am your father\n";
            writer.write(s, 0, s.length());
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    void testReadingFileUsingByteChannel() {
        Path file = Paths.get("io_files", "xanadu.txt");
        // 默认只读
        try (SeekableByteChannel channel = Files.newByteChannel(file)) {
            ByteBuffer buf = ByteBuffer.allocate(64);
            Charset charset = StandardCharsets.UTF_8;
            while (channel.read(buf) > 0) {
                buf.flip();
                System.out.println(charset.decode(buf));
                buf.clear();
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    void testWritingFileUsingByteChannel() {
        Path file = Paths.get("io_files", "xanadu.txt");
        Set<? extends OpenOption> optionSet = EnumSet.of(
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ByteBuffer buf = ByteBuffer.wrap(
                "I am your daddy\n".getBytes(StandardCharsets.UTF_8));
        try (SeekableByteChannel channel = Files.newByteChannel(file, optionSet)) {
            channel.write(buf);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    void testRandomAccessFile() {
        Path file = Paths.get("io_files", "xanadu.txt");
        String s = "I am here!\n";
        ByteBuffer out = ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8));
        ByteBuffer copy = ByteBuffer.allocate(12);
        try (FileChannel fileChannel = FileChannel.open(
                file, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            // 从文件头读取足够塞满 copy 的字节
            int readCount;
            do {
                readCount = fileChannel.read(copy);
            } while (readCount != -1 && copy.hasRemaining());

            // 写入文件头
            fileChannel.position(0);
            while (out.hasRemaining()) {
                fileChannel.write(out);
            }
            out.rewind();

            fileChannel.position(fileChannel.size() - 1);
            copy.flip();
            while (copy.hasRemaining()) {
                fileChannel.write(copy);
            }
            while (out.hasRemaining()) {
                fileChannel.write(out);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    void testFileMIMEType() throws IOException {
        Path filename = Paths.get("io_files/hello.html");
        System.out.println(Files.exists(filename));
        System.out.println(Files.probeContentType(filename));
    }

    @Test
    void testCountCharacters() throws IOException {
        Path file = Paths.get("io_files", "xanadu.txt");
        System.out.println(CountCharacters.countCharacters(file, ','));
    }
}
