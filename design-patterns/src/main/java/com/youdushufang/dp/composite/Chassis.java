package com.youdushufang.dp.composite;

import com.youdushufang.dp.visitor.EquipmentVisitor;

public class Chassis extends EquipmentWithChildren {

    public Chassis(String name) {
        super(name);
    }

    @Override
    public void accept(EquipmentVisitor visitor) {
        for (Equipment child : children) {
            child.accept(visitor);
        }
        visitor.visitChassis(this);
    }
}
