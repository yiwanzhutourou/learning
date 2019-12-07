package com.youdushufang.dp.mediator;

public class TextView extends Widget {

    private String text;

    public TextView(String text, DialogDirector dialogDirector) {
        super(dialogDirector);
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
        changed();
    }

    @Override
    public void show() {
        System.out.println("TextView: " + text);
    }
}
