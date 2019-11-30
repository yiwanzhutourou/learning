package com.youdushufang.reflection.fields;

enum Tweedle { DEE, DUM }

class Book {
    long chapters = 0;
    private String[] characters = { "Alice", "White Rabbit" };
    final Tweedle twin = Tweedle.DEE;
}
