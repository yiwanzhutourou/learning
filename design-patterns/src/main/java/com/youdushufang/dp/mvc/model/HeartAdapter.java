package com.youdushufang.dp.mvc.model;

public class HeartAdapter implements BeatModelInterface {

    private HearModelInterface hearModel;

    public HeartAdapter(HearModelInterface hearModel) {
        this.hearModel = hearModel;
    }

    @Override
    public void initialize() {}

    @Override
    public void on() {}

    @Override
    public void off() {}

    @Override
    public void setBPM(int bpm) {}

    @Override
    public int getBPM() {
        return hearModel.getHeartRate();
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        hearModel.registerBeatObserver(observer);
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
        hearModel.removeBeatObserver(observer);
    }

    @Override
    public void registerObserver(BPMObserver observer) {
        hearModel.registerBPMObserver(observer);
    }

    @Override
    public void unregisterObserver(BPMObserver observer) {
        hearModel.removeBPMObserver(observer);
    }
}
