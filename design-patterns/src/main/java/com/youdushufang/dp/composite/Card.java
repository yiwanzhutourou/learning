package com.youdushufang.dp.composite;

public class Card extends AbstractEquipment {

    public Card(String name) {
        super(name);
    }

    @Override
    public int power() {
        return 50;
    }
}
