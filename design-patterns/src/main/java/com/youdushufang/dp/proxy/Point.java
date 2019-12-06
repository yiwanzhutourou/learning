package com.youdushufang.dp.proxy;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Point {

    public static final Point ZERO = new Point();

    private int x;

    private int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Point setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Point setY(int y) {
        this.y = y;
        return this;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
