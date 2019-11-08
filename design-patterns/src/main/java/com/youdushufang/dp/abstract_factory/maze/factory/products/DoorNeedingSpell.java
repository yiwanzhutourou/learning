package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 角色：具体产品（Concrete Product）
 */
public class DoorNeedingSpell extends Door {

    public DoorNeedingSpell(Room r1, Room r2) {
        super(r1, r2);
    }

    @Override
    public String toString() {
        return "DoorNeedingSpell(Room " + r1.getId() + ", Room " + r2.getId() + ")";
    }
}
