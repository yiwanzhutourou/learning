package com.youdushufang.dp.observer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalClock implements Widget, Observer {

    private ClockTimer clockTimer;

    private Date time;

    private SimpleDateFormat timeFormatter;

    public DigitalClock(ClockTimer clockTimer) {
        this.clockTimer = clockTimer;
        clockTimer.attach(this);
        this.time = new Date();
        this.timeFormatter = new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof ClockTimer) {
            time.setTime(((ClockTimer) subject).getTime());
            draw();
        }
    }

    @Override
    public void draw() {
        System.out.println(timeFormatter.format(time));
    }

    @Override
    public void destroy() {
        clockTimer.detach(this);
    }
}
