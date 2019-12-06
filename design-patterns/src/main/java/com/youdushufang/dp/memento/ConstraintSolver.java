package com.youdushufang.dp.memento;

import com.youdushufang.dp.proxy.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ConstraintSolver {

    private static ConstraintSolver INSTANCE;

    private Map<Constraint, Curve> connections;

    private static final Random random = new Random();

    private ConstraintSolver() {
        this.connections = new HashMap<>();
    }

    public void solve() {
        connections.replaceAll((k, v) -> generateCurve(k.start, k.end));
    }

    public void addConstraint(Graphic start, Graphic end) {
        connections.put(new Constraint(start, end), generateCurve(start, end));
    }

    public void removeConstraint(Graphic start, Graphic end) {
        connections.remove(new Constraint(start, end));
    }

    public Memento createMemento() {
        Memento memento = new Memento();
        memento.connections = new HashMap<>(connections);
        return memento;
    }

    public void setMemento(Memento memento) {
        this.connections = memento.connections;
    }

    public static ConstraintSolver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConstraintSolver();
        }
        return INSTANCE;
    }

    public static class Memento {
        private Map<Constraint, Curve> connections;
    }

    private Curve generateCurve(Graphic start, Graphic end) {
        float middle = random.nextFloat();
        Point startPoint = start.getStartPoint();
        Point endPoint = end.getStartPoint();
        // 模拟一个随机的中间点
        Point middlePoint = new Point(
                (int) (middle * (startPoint.getX() + endPoint.getX()) / 2f),
                (int) (middle * (startPoint.getY() + endPoint.getY()) / 2f)
        );
        return Curve.newCurve(startPoint, middlePoint, endPoint);
    }

    @Override
    public String toString() {
        return connections.toString();
    }

    private static class Constraint {

        private Graphic start;

        private Graphic end;

        public Constraint(Graphic start, Graphic end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Constraint that = (Constraint) o;
            return Objects.equals(start, that.start) &&
                    Objects.equals(end, that.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return "[" + start.getId() + "-" + end.getId() + "]";
        }
    }
}
