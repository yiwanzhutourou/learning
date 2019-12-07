package com.youdushufang.dp.mediator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DialogDirectorTest {

    @Test
    public void testSomeDialogDirector() {
        SomeDialogDirector someDialogDirector = new SomeDialogDirector();
        Button button = new Button("Hello, Mediator", someDialogDirector);
        TextView textView = new TextView("Hello, World", someDialogDirector);
        ListBox listBox = new ListBox(
                Arrays.asList("Hello, List", "Hello, Mediator"), someDialogDirector);
        someDialogDirector.setButton(button);
        someDialogDirector.setTextView(textView);
        someDialogDirector.setListBox(listBox);

        button.show();
        textView.show();
        listBox.show();

        button.onClick();
        System.out.println();

        button.show();
        textView.show();
        listBox.show();
    }
}
