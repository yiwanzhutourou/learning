package com.youdushufang.dp.bridge;

public class TransientWindow extends Window {

    @Override
    void draw() {
        getWindowImp().drawRect();
    }
}
