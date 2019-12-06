package com.youdushufang.dp.memento;

import com.youdushufang.dp.proxy.Point;

import java.util.Objects;

public abstract class Graphic {

    private int id;

    private Point startPoint;

    public Graphic(int id, Point startPoint) {
        this.id = id;
        this.startPoint = startPoint;
    }

    public int getId() {
        return id;
    }

    public void move(int x, int y) {
        startPoint.move(x, y);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graphic graphic = (Graphic) o;
        return id == graphic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
