package com.youdushufang.visible_scope.bar;

import com.youdushufang.visible_scope.foo.VisibleScope;

public class VisibleScopeImpl extends VisibleScope {

    // public 对包外可见、protected 对包外子类可见
    public void testVisibleScope() {
        publicMethod();
        protectedMethod();
    }
}
