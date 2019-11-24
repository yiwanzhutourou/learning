package com.youdushufang.dp.facade.compiler;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EchoCompilerTest {

    @Test
    public void testEchoCompilerFacade() {
        System.out.println("// compile start...");
        // Compiler Facade
        Compiler compiler = new EchoCompiler();
        // 要编译的输入
        InputStream inputStream = new ByteArrayInputStream(
                "Hello, world\n你好世界     I am your father, OK?".getBytes(StandardCharsets.UTF_8));
        // 输出
        ByteCodeStream byteCodeStream = new ByteCodeStream();
        try {
            // 执行编译
            compiler.compile(inputStream, byteCodeStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(byteCodeStream.getAsString(StandardCharsets.UTF_8));
        System.out.println("// compile end...");
    }
}
