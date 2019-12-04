package com.youdushufang.dp.adapter;

import com.youdushufang.dp.strategy.Coordination;

/**
 * 角色：Adaptee
 */
public class TextView {

    private String text;

    private int top;

    private int left;

    public TextView(String text, int top, int left) {
        this.text = text;
        this.top = top;
        this.left = left;
    }

    public Coordination getOrigin() {
        return new Coordination(left, top);
    }

    public Coordination getExtent() {
        return new Coordination(text.length(), 1);
    }

    public boolean isEmpty() {
        return text == null || "".equals(text);
    }
}
