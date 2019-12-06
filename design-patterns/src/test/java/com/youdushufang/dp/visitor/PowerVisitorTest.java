package com.youdushufang.dp.visitor;

import com.youdushufang.dp.composite.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PowerVisitorTest {

    @Test
    public void testTotalPower() {
        Cabinet cabinet = new Cabinet("PC Cabinet");
        Chassis chassis = new Chassis("PC Chassis");
        cabinet.add(chassis);

        Bus bus = new Bus("MCA Bus");
        bus.add(new Card("16Mbs Token Ring"));

        chassis.add(bus);
        chassis.add(new FloppyDisk("3.5in Floppy"));

        PowerVisitor powerVisitor = new PowerVisitor();
        cabinet.accept(powerVisitor);
        Assertions.assertEquals(150, powerVisitor.getPower());

        powerVisitor.reset();
        bus.accept(powerVisitor);
        Assertions.assertEquals(50, powerVisitor.getPower());

        powerVisitor.reset();
        chassis.accept(powerVisitor);
        Assertions.assertEquals(150, powerVisitor.getPower());
    }
}
