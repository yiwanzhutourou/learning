package com.youdushufang.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class CopyCharacters {

    private CopyCharacters() {}

    /**
     * 将内容按字符从一个文件拷贝到另一个文件
     *
     * @param from 源文件路径
     * @param to   目标文件路径
     */
    public static void copyFileChars(String from, String to) throws IOException {
        try (FileReader in = new FileReader(from);
             FileWriter out = new FileWriter(to)) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }
    }

    /**
     * 将内容按行从一个文件拷贝到另一个文件
     *
     * @param from 源文件路径
     * @param to   目标文件路径
     */
    public static void copyFileLines(String from, String to) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(from));
             PrintWriter out = new PrintWriter(new FileWriter(to))) {
            String line;
            while ((line = in.readLine()) != null) {
                log.info(line);
                out.println(line);
            }
        }
    }
}
