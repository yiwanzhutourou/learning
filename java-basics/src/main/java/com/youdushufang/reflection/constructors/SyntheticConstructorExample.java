package com.youdushufang.reflection.constructors;

class SyntheticConstructorExample {

    private SyntheticConstructorExample() { }

    class Inner {
        // Compiler will generate a synthetic constructor since
        // SyntheticConstructor() is private.
        Inner() { new SyntheticConstructorExample(); }
    }
}
