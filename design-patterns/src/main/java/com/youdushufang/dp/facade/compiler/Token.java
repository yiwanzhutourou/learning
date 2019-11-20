package com.youdushufang.dp.facade.compiler;

/**
 * 标记封装类
 *
 * 标记（token）是对编译器有用的程序的最小元素
 */
public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
