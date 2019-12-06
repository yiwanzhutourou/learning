package com.youdushufang.dp.chain_of_responsibility;

public class Application extends HelpHandler {

    public Application(Topic topic) {
        super(null, topic);
    }

    @Override
    protected void handleHelp() {
        System.out.println("I can help, said " + this);
    }
}
