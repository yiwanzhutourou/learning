package com.youdushufang.dp.adapter;

import com.youdushufang.dp.strategy.Coordination;

/**
 * 角色：Adapter
 */
public class TextShape implements Shape {

    private TextView textView;

    public TextShape(TextView textView) {
        this.textView = textView;
    }

    @Override
    public Rectangle boundingBox() {
        Coordination origin = textView.getOrigin();
        Coordination extent = textView.getExtent();
        return new Rectangle(origin.getX(), origin.getY(),
                origin.getX() + extent.getX(), origin.getY() + extent.getY());
    }

    @Override
    public Manipulator createManipulator() {
        return new TextManipulator(this);
    }

    public boolean isEmpty() {
        return textView.isEmpty();
    }
}
