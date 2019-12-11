package com.youdushufang.dp.mvc.model;

public interface BeatModelInterface {

    void initialize();

    void on();

    void off();

    void setBPM(int bpm);

    int getBPM();

    void registerObserver(BeatObserver observer);

    void unregisterObserver(BeatObserver observer);

    void registerObserver(BPMObserver observer);

    void unregisterObserver(BPMObserver observer);
}
