package com.youdushufang.test.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

@Slf4j
class ScannerTest {

    @Test
    void testScanner() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(
                new BufferedReader(new FileReader("io_files/xanadu.txt")))) {
            scanner.useDelimiter(",\\s*");
            while (scanner.hasNext()) {
                log.info(scanner.next());
            }
        }
    }
}
