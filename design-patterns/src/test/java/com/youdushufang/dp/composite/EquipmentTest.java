package com.youdushufang.dp.composite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EquipmentTest {

    @Test
    public void testPower() {
        Cabinet cabinet = new Cabinet("PC Cabinet");
        Chassis chassis = new Chassis("PC Chassis");
        cabinet.add(chassis);

        Bus bus = new Bus("MCA Bus");
        bus.add(new Card("16Mbs Token Ring"));

        chassis.add(bus);
        chassis.add(new FloppyDisk("3.5in Floppy"));
        System.out.println(cabinet.power());
        Assertions.assertEquals(50, bus.power());
        Assertions.assertEquals(150, chassis.power());
        Assertions.assertEquals(150, cabinet.power());
    }
}
