package com.youdushufang.dp.facade.compiler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Echo Compiler compile 接口实际上只是将输入的字节流按照字符串解析，
 * 之后每一个单词按单独的一行直接输出为 UTF-8 字节流
 */
public class EchoCompiler implements Compiler {

    @Override
    public void compile(InputStream input, ByteCodeStream output) throws IOException {
        Scanner scanner = new EchoScanner(input);
        ProgramNodeBuilder programNodeBuilder = new ExpressionNodeBuilder();
        Parser parser = new EchoParser();

        parser.parse(scanner, programNodeBuilder);

        CodeGenerator codeGenerator = new EchoCodeGenerator(output);
        ProgramNode parseTree = programNodeBuilder.getRootNode();
        parseTree.traverse(codeGenerator);
    }
}
