package com.youdushufang.circulation;

public class Bar {

    private Foo foo;

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public void doSomething() {
        System.out.println("do something in Bar");
        if (foo != null) {
            foo.doSomething();
        }
    }
}
