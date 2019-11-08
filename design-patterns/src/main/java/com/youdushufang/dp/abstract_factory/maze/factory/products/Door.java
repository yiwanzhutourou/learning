package com.youdushufang.dp.abstract_factory.maze.factory.products;

import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Door implements RoomSide {

    final Room r1;

    final Room r2;

    public Door(Room r1, Room r2) {
        Objects.requireNonNull(r1);
        Objects.requireNonNull(r2);
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public String toString() {
        return "Door(Room " + r1.getId() + ", Room " + r2.getId() + ")";
    }
}
