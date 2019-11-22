package com.youdushufang.visible_scope.foo;

public class Foo {

    // public 对包内可见、protected 对包内子类可见、无修饰符对包内可见
    public void testVisible() {
        VisibleScope visibleScope = new VisibleScope();
        visibleScope.publicMethod();
        visibleScope.protectedMethod();
        visibleScope.noneMethod();
    }
}
