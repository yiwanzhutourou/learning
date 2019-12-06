package com.youdushufang.dp.composite;

import com.youdushufang.dp.visitor.EquipmentVisitor;

public class Card extends AbstractEquipment {

    public Card(String name) {
        super(name);
    }

    @Override
    public int power() {
        return 50;
    }

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visitCard(this);
    }
}
