package com.youdushufang.dp.mvc.controller;

import com.youdushufang.dp.mvc.model.BeatModelInterface;
import com.youdushufang.dp.mvc.view.DJView;
import com.youdushufang.dp.mvc.view.DJViewInterface;

public class BeatController implements ControllerInterface {

    private BeatModelInterface model;

    private DJViewInterface view;

    public BeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(this, model);
        view.initView();
        view.enableStartMenuItem();
        view.disableStopMenuItem();
        model.initialize();
    }

    @Override
    public void start() {
        model.on();
        view.disableStartMenuItem();
        view.enableStopMenuItem();
    }

    @Override
    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }

    @Override
    public void increaseBPM() {
        model.setBPM(model.getBPM() + 1);
    }

    @Override
    public void decreaseBPM() {
        model.setBPM(model.getBPM() - 1);
    }

    @Override
    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }
}
