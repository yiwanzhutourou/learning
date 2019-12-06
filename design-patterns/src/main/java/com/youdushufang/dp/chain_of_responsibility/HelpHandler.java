package com.youdushufang.dp.chain_of_responsibility;

public abstract class HelpHandler {

    private HelpHandler successor;

    private Topic topic;

    public HelpHandler(HelpHandler successor, Topic topic) {
        this.successor = successor;
        this.topic = topic;
    }

    protected boolean hasHelp() {
        return topic != Topic.NO_HELP_TOPIC;
    }

    protected void handleHelp() {
        if (successor != null) {
            successor.handleHelp();
        }
    }
}
