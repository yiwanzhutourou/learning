package com.youdushufang.dp.bridge;

public abstract class Window {

    private WindowImp imp;

    protected WindowImp getWindowImp() {
        if (imp == null) {
            imp = WindowImpFactory.getInstance().createWindowImp();
        }
        return imp;
    }

    abstract void draw();
}
