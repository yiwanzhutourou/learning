package com.youdushufang.dp.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenFileTest {

    public static void main(String[] args) {
        Application application = new Application();
        OpenCommand openCommand = new OpenCommand(application);
        try {
            openCommand.execute();
            openCommand.undo();
            openCommand.undo();
        } catch (CommandException e) {
            log.error("Open file failed: " + e.getMessage(), e, e.getTarget());
        }
    }
}
