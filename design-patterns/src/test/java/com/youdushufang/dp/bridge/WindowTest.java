package com.youdushufang.dp.bridge;

import org.junit.jupiter.api.Test;

public class WindowTest {

    @Test
    public void testWindowImp() {
        Window iconWindow = new IconWindow();
        iconWindow.draw();
        Window transientWindow = new TransientWindow();
        transientWindow.draw();
    }
}
