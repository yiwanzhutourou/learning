package com.youdushufang.dp.memento;

import com.youdushufang.dp.proxy.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Curve {

    private List<Point> points;

    private Curve(List<Point> points) {
        Objects.requireNonNull(points);
        this.points = points;
    }

    public static Curve newCurve(Point... points) {
        return new Curve(Arrays.asList(points));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Point point : points) {
            stringBuilder.append("(").append(point.getX())
                    .append(", ").append(point.getY()).append(")");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
