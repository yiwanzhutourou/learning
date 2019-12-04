package com.youdushufang.dp.command;

public interface Command {

    void execute() throws CommandException;

    void undo() throws CommandException;
}
