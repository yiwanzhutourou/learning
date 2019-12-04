package com.youdushufang.dp.command;

public class CommandException extends Exception {

    private Throwable target;

    public CommandException(Throwable target, String message) {
        super(message);
        this.target = target;
    }

    public Throwable getTarget() {
        return target;
    }
}
