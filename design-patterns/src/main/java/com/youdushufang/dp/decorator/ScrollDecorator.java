package com.youdushufang.dp.decorator;

public class ScrollDecorator extends Decorator {

    public ScrollDecorator(VisualComponent visualComponent) {
        super(visualComponent);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("--- scroll me ---");
    }
}
