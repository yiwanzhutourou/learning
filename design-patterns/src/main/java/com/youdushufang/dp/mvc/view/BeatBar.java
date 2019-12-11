package com.youdushufang.dp.mvc.view;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class BeatBar extends JProgressBar implements Runnable {

    BeatBar() {
        Thread thread = new Thread(this);
        setMaximum(100);
        thread.start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        for (;;) {
            setValue((int) (getValue() * .75));
            repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
