package com.youdushufang.dp.adapter;

import lombok.Data;

@Data
public class Rectangle {

    private int left;

    private int top;

    private int right;

    private int bottom;

    public Rectangle(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
