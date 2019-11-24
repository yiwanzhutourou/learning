package com.youdushufang.dp.facade.compiler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模拟一个词法分析器，其实就是把输入流中的单词按空格分开
 */
public class EchoScanner extends Scanner {

    private List<String> words;

    private int index = 0;

    public EchoScanner(InputStream input) {
        super(input);

        String text = new BufferedReader(new InputStreamReader(input))
                .lines()
                .collect(Collectors.joining("\n"));
        words = List.of(text.split("\\s+"));
    }

    @Override
    public Token scan() {
        if (index < words.size()) {
            return new Token(words.get(index++));
        }
        return null;
    }
}
