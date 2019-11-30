package com.youdushufang.dp.observer;

public class ClockTimer extends AbstractSubject {

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void tick() {
        notifyObservers();
    }
}
