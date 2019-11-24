package com.youdushufang.dp.decorator;

import org.junit.jupiter.api.Test;

public class WindowTest {

    @Test
    public void testDrawWindow() {
        Window window = new Window();
        window.addComponent(new BorderDecorator(
                new TextView("Hello, world"), 8));
        window.addComponent(new BorderDecorator(
                new ScrollDecorator(new TextView("scrolled text")), 8));
        window.draw();
    }
}
