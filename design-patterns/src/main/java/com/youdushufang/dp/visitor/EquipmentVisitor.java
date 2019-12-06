package com.youdushufang.dp.visitor;

import com.youdushufang.dp.composite.*;

public interface EquipmentVisitor {

    void visitFloppyDisk(FloppyDisk floppyDisk);

    void visitCard(Card card);

    void visitChassis(Chassis chassis);

    void visitBus(Bus bus);

    void visitCabinet(Cabinet cabinet);
}
