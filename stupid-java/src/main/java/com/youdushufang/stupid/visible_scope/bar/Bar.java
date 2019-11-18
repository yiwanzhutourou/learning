package com.youdushufang.stupid.visible_scope.bar;

import com.youdushufang.stupid.visible_scope.foo.VisibleScope;

public class Bar {

    // public 对包外可见
    public void testVisibleScope() {
        VisibleScope visibleScope = new VisibleScope();
        visibleScope.publicMethod();
    }
}
