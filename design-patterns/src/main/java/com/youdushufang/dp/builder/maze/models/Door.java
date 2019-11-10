package com.youdushufang.dp.builder.maze.models;

import java.util.Objects;

class Door implements RoomSide {

    private final Room r1;

    private final Room r2;

    Door(Room r1, Room r2) {
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
