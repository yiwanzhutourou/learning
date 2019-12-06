package com.youdushufang.dp.bridge;

public class XWindowImp implements WindowImp {

    @Override
    public void drawRect() {
        System.out.println("drawRect in X Window");
    }

    @Override
    public void drawBitmap() {
        System.out.println("drawBitmap in X Window");
    }
}
