package com.youdushufang.dp.memento;

import com.youdushufang.dp.proxy.Point;
import org.junit.jupiter.api.Test;

public class ConstraintSolverTest {

    @Test
    public void testConstraintSolverMemento() {
        ConstraintSolver solver = ConstraintSolver.getInstance();
        Graphic rec1 = new Rectangle(1, new Point(100, 100));
        Graphic rec2 = new Rectangle(2, new Point(200, 200));
        Graphic rec3 = new Rectangle(3, new Point(300, 300));
        solver.addConstraint(rec1, rec2);
        solver.addConstraint(rec1, rec3);
        System.out.println(solver);
        ConstraintSolver.Memento memento = solver.createMemento();
        rec1.move(300, 300);
        solver.solve();
        System.out.println(solver);
        rec1.move(-300, -300);
        solver.setMemento(memento);
        System.out.println(solver);
    }
}
