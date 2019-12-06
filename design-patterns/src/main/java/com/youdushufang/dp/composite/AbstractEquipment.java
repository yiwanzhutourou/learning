package com.youdushufang.dp.composite;

import java.util.Iterator;

public abstract class AbstractEquipment implements Equipment {

    private String name;

    AbstractEquipment(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int power() {
        int total = 0;
        Iterator<Equipment> iterator = iterator();
        while (iterator.hasNext()) {
            total += iterator.next().power();
        }
        return total;
    }

    @Override
    public void add(Equipment equipment) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    public void remove(Equipment equipment) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public Iterator<Equipment> iterator() {
        return new NullIterator<>();
    }
}
