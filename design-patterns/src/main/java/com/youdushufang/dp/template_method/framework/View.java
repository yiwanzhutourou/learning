package com.youdushufang.dp.template_method.framework;

public abstract class View {

    public final void display() {
        measure();
        layout();
        draw();
    }

    private void measure() {
        onMeasure();
        System.out.println("View - measure");
    }

    private void layout() {
        onLayout();
        System.out.println("View - layout");
    }

    private void draw() {
        onDraw();
        System.out.println("View - draw");
    }

    protected void onMeasure() { }

    protected void onLayout() { }

    protected void onDraw() { }
}
