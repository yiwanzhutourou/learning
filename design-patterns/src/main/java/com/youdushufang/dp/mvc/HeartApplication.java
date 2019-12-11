package com.youdushufang.dp.mvc;

import com.youdushufang.dp.mvc.controller.HeartController;
import com.youdushufang.dp.mvc.model.HearModelInterface;
import com.youdushufang.dp.mvc.model.HeatrModel;

public class HeartApplication {

    public static void main(String[] args) {
        HearModelInterface model = new HeatrModel();
        new HeartController(model);
    }
}
