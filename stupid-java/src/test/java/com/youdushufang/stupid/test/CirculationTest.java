package com.youdushufang.stupid.test;

import com.youdushufang.stupid.circulation.Bar;
import com.youdushufang.stupid.circulation.Foo;
import org.junit.jupiter.api.Test;

class CirculationTest {

    // 测试 Java 类是否可以循环引用
    // 编译没问题，但是肯定不推荐这么写，不管是类，还是模块的循环引用都不是一个好的实践
    // 例如下面的例子会导致循环调用
    @Test
    void test() {
        Foo foo = new Foo();
        Bar bar = new Bar();

        foo.setBar(bar);
        bar.setFoo(foo);

        foo.doSomething();
        bar.doSomething();
    }
}
