package com.youdushufang.dp.mvc;

import com.youdushufang.dp.mvc.controller.BeatController;
import com.youdushufang.dp.mvc.model.BeatModel;
import com.youdushufang.dp.mvc.model.BeatModelInterface;

public class DJApplication {

    public static void main(String[] args) {
        BeatModelInterface model = new BeatModel();
        new BeatController(model);
    }
}
