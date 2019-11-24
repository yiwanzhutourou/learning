package com.youdushufang.dp.decorator;

public class TextView extends VisualComponent {

    private String text;

    public TextView(String text) {
        this.text = text;
    }

    @Override
    public void draw() {
        System.out.println(text);
    }

    @Override
    public void resize() {

    }
}
