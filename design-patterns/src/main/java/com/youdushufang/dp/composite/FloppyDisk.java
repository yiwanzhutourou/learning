package com.youdushufang.dp.composite;

import com.youdushufang.dp.visitor.EquipmentVisitor;

public class FloppyDisk extends AbstractEquipment {

    public FloppyDisk(String name) {
        super(name);
    }

    @Override
    public int power() {
        return 100;
    }

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visitFloppyDisk(this);
    }
}
