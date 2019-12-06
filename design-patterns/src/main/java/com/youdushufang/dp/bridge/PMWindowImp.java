package com.youdushufang.dp.bridge;

public class PMWindowImp implements WindowImp {

    @Override
    public void drawRect() {
        System.out.println("drawRect in PM");
    }

    @Override
    public void drawBitmap() {
        System.out.println("drawBitmap in PM");
    }
}
