package com.youdushufang.dp.decorator;

public abstract class Decorator extends VisualComponent {

    private final VisualComponent visualComponent;

    public Decorator(VisualComponent visualComponent) {
        this.visualComponent = visualComponent;
    }

    @Override
    public void draw() {
        visualComponent.draw();
    }

    @Override
    public void resize() {
        visualComponent.resize();
    }
}
