package com.youdushufang.dp.mvc.controller;

import com.youdushufang.dp.mvc.model.HearModelInterface;
import com.youdushufang.dp.mvc.model.HeartAdapter;
import com.youdushufang.dp.mvc.view.DJView;
import com.youdushufang.dp.mvc.view.DJViewInterface;

public class HeartController implements ControllerInterface {

    public HeartController(HearModelInterface model) {
        DJViewInterface view = new DJView(this, new HeartAdapter(model));
        view.initView();
        view.disableStartMenuItem();
        view.disableStopMenuItem();
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void increaseBPM() {}

    @Override
    public void decreaseBPM() {}

    @Override
    public void setBPM(int bpm) {}
}
