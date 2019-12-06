package com.youdushufang.dp.chain_of_responsibility;

public class Dialog extends Widget {

    public Dialog(HelpHandler successor, Topic topic) {
        super(successor, topic);
    }

    @Override
    protected void handleHelp() {
        if (hasHelp()) {
            System.out.println("I can help, said " + this);
        } else {
            super.handleHelp();
        }
    }
}
