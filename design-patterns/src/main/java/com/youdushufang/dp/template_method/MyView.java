package com.youdushufang.dp.template_method;

import com.youdushufang.dp.template_method.framework.View;

public class MyView extends View {

    @Override
    protected void onMeasure() {
        super.onMeasure();
        System.out.println("MyView - onMeasure");
    }

    @Override
    protected void onLayout() {
        super.onLayout();
        System.out.println("MyView - onLayout");
    }

    @Override
    protected void onDraw() {
        super.onDraw();
        System.out.println("MyView - onDraw");
    }
}
