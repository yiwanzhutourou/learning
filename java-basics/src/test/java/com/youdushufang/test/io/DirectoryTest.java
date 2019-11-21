package com.youdushufang.test.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

class DirectoryTest {

    @Test
    void testListDirectoryContents() {
        Path directory = Paths.get("io_files");
        DirectoryStream.Filter<Path> filter = file -> file.toString().endsWith(".txt");
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory, filter)) {
            for (Path file : directoryStream) {
                System.out.println(file);
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println(e);
        }
    }

    @Test
    void testWalkingFileTree() throws IOException {
        Path directory = Paths.get(".");
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                PathMatcher matcher = FileSystems.getDefault()
                        .getPathMatcher("glob:*.java");
                if (matcher.matches(file.getFileName())) {
                    System.out.println(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.startsWith("./.idea") || dir.startsWith("./target")) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                exc.printStackTrace();
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
