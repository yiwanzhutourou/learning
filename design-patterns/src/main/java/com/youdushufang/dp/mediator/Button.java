package com.youdushufang.dp.mediator;

public class Button extends Widget {

    private String text;

    public Button(String text, DialogDirector dialogDirector) {
        super(dialogDirector);
        this.text = text;
    }

    public void onClick() {
        changed();
    }

    public String getText() {
        return text;
    }

    @Override
    public void show() {
        System.out.println("Button: " + text);
    }
}
