package com.youdushufang.dp.composite;

public class FloppyDisk extends AbstractEquipment {

    public FloppyDisk(String name) {
        super(name);
    }

    @Override
    public int power() {
        return 100;
    }
}
