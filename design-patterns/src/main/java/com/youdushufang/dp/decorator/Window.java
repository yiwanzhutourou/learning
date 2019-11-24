package com.youdushufang.dp.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Window implements Drawable {

    private List<VisualComponent> components;

    public Window() {
        this.components = new ArrayList<>();
    }

    public void addComponent(VisualComponent component) {
        Objects.requireNonNull(component);
        components.add(component);
    }

    @Override
    public void draw() {
        for (VisualComponent component : components) {
            component.draw();
        }
    }
}
