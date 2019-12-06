package com.youdushufang.dp.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

abstract class EquipmentWithChildren extends AbstractEquipment {

    List<Equipment> children;

    EquipmentWithChildren(String name) {
        super(name);
        children = new ArrayList<>();
    }

    @Override
    public void add(Equipment equipment) {
        Objects.requireNonNull(equipment);
        children.add(equipment);
    }

    @Override
    public void remove(Equipment equipment) {
        Objects.requireNonNull(equipment);
        children.remove(equipment);
    }

    @Override
    public Iterator<Equipment> iterator() {
        return children.iterator();
    }
}
