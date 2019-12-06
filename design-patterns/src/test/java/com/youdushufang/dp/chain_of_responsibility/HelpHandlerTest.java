package com.youdushufang.dp.chain_of_responsibility;

import org.junit.jupiter.api.Test;

public class HelpHandlerTest {

    @Test
    public void testApplication() {
        Application application = new Application(Topic.APPLICATION_TOPIC);
        Dialog dialog = new Dialog(application, Topic.PRINT_TOPIC);
        Button button1 = new Button(dialog, Topic.PAPER_ORIENTATION_TOPIC);
        button1.handleHelp();
        Button button2 = new Button(dialog, Topic.NO_HELP_TOPIC);
        button2.handleHelp();
        Button button3 = new Button(new Dialog(application, Topic.NO_HELP_TOPIC), Topic.NO_HELP_TOPIC);
        button3.handleHelp();
    }
}
