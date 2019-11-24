package com.youdushufang.dp.decorator;

public class BorderDecorator extends Decorator {

    private int borderWidth;

    public BorderDecorator(VisualComponent visualComponent, int borderWidth) {
        super(visualComponent);
        if (borderWidth < 0) {
            throw new IllegalArgumentException("negative border");
        }
        this.borderWidth = borderWidth;
    }

    @Override
    public void draw() {
        drawUpBorder();
        super.draw();
        drawDownBorder();
    }

    private void drawUpBorder() {
        for (int i = 0; i < borderWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private void drawDownBorder() {
        for (int i = 0; i < borderWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
