package com.youdushufang.visible_scope.foo;

public class VisibleScope {

    public void publicMethod() { }

    protected void protectedMethod() { }

    void noneMethod() { }

    // private 仅对类内部可见
    private void privateMethod() { }

    private class InnerClass {

        // 内部非静态类可以访问外部 private 修饰的非静态方法
        private void testVisibleScope() {
            privateMethod();
        }
    }
}
