package com.youdushufang.stupid.circulation;

public class Foo {

    private Bar bar;

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public void doSomething() {
        System.out.println("do something in Foo");
        if (bar != null) {
            bar.doSomething();
        }
    }
}
