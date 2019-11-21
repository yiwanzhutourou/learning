package com.youdushufang.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {

    private CopyBytes() {}

    /**
     * 将内容按字节从一个文件拷贝到另一个文件
     *
     * @param from 源文件路径
     * @param to   目标文件路径
     */
    public static void copyFileBytes(String from, String to) throws IOException {
        try (FileInputStream in = new FileInputStream(from);
             FileOutputStream out = new FileOutputStream(to)) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }
    }
}
