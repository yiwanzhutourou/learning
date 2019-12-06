package com.youdushufang.dp.chain_of_responsibility;

public abstract class Widget extends HelpHandler {

    public Widget(HelpHandler successor, Topic topic) {
        super(successor, topic);
    }
}
