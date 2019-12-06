package com.youdushufang.dp.visitor;

import com.youdushufang.dp.composite.*;

public class PowerVisitor implements EquipmentVisitor {

    private int total = 0;

    @Override
    public void visitFloppyDisk(FloppyDisk floppyDisk) {
        total += floppyDisk.power();
    }

    @Override
    public void visitCard(Card card) {
        total += card.power();
    }

    @Override
    public void visitChassis(Chassis chassis) {
        // do nothing
    }

    @Override
    public void visitBus(Bus bus) {
        // do nothing
    }

    @Override
    public void visitCabinet(Cabinet cabinet) {
        // do nothing
    }

    public int getPower() {
        return total;
    }

    public void reset() {
        total = 0;
    }
}
