package com.youdushufang.dp.observer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class DigitalClockTest {

    @Test
    public void testDrawTime() throws InterruptedException {
        ClockTimer timer = new ClockTimer();
        DigitalClock clock = new DigitalClock(timer);
        for (int i = 0; i < 5; i++) {
            timer.tick();
            TimeUnit.SECONDS.sleep(1);
        }
        clock.destroy();
        System.out.println("detached");
        timer.tick();
    }
}
