package com.youdushufang.dp.facade.compiler;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * 字节码流封装类
 * 模拟一个内存写入流，最后结果可以通过 getAsString 获取
 */
public class ByteCodeStream extends ByteArrayOutputStream {

    public String getAsString(Charset charset) {
        return new String(toByteArray(), charset);
    }
}
