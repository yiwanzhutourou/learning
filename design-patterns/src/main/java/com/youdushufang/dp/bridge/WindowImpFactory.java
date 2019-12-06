package com.youdushufang.dp.bridge;

public class WindowImpFactory {

    public static WindowImpFactory INSTANCE;

    private WindowImpFactory() { }

    public static WindowImpFactory getInstance()  {
        if (INSTANCE == null) {
            INSTANCE = new WindowImpFactory();
        }
        return INSTANCE;
    }

    public WindowImp createWindowImp() {
        return new PMWindowImp();
    }
}
