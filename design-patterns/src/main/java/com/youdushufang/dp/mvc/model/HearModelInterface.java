package com.youdushufang.dp.mvc.model;

public interface HearModelInterface {

    int getHeartRate();

    void registerBeatObserver(BeatObserver observer);

    void removeBeatObserver(BeatObserver observer);

    void registerBPMObserver(BPMObserver observer);

    void removeBPMObserver(BPMObserver observer);
}
