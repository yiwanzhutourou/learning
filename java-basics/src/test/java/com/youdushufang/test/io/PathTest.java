package com.youdushufang.test.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class PathTest {

    @Test
    void testPathRelatedMethods() throws IOException {
        Path path = Paths.get("/home/joe/foo");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0, 2): %s%n", path.subpath(0, 2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
        System.out.format("resolve: %s%n", path.resolve("bar"));

        System.out.println();

        Path relativePath = Paths.get("io_files/xanadu.txt");
        System.out.format("toString: %s%n", relativePath.toString());
        System.out.format("getFileName: %s%n", relativePath.getFileName());
        System.out.format("getName(0): %s%n", relativePath.getName(0));
        System.out.format("getNameCount: %d%n", relativePath.getNameCount());
        System.out.format("subpath(0, 2): %s%n", relativePath.subpath(0, 2));
        System.out.format("getParent: %s%n", relativePath.getParent());
        System.out.format("getRoot: %s%n", relativePath.getRoot());
        System.out.format("toRealPath: %s%n", relativePath.toRealPath());
        System.out.format("toAbsolutePath: %s%n", relativePath.toAbsolutePath());

        System.out.println();

        // p1.relativize(p2) 返回 p2 相对于 p1 的路径，也就是从 p1 到达 p2 需要走过的路径
        Path p1 = Paths.get("/home");
        Path p2 = Paths.get("/home/sally/bar");
        System.out.println(p1.relativize(p2)); // sally/home
        System.out.println(p2.relativize(p1)); // ../..
        Path p3 = Paths.get("sally");
        Path p4 = Paths.get("joe");
        System.out.println(p3.relativize(p4)); // ../joe
        System.out.println(p4.relativize(p3)); // ../sally
    }

    @Test
    void testPathComparing() {
        Path p1 = Paths.get("/home/sally/bar");
        Path p2 = p1.resolve("foo");
        System.out.println(p1.startsWith(Paths.get("/home")));
        System.out.println(p2.endsWith("foo"));
        System.out.println(p1.equals(p2));
    }

    @Test
    void testPathIterator() {
        Path path = Paths.get("/home/sally/bar");
        for (Path name : path) {
            System.out.println(name);
        }
    }
}
