package com.youdushufang.dp.builder.maze.models;

import com.youdushufang.dp.constants.Orientation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Room {

    private int id;

    private Map<Orientation, RoomSide> roomSideMap;

    Room(int id) {
        this.id = id;
        this.roomSideMap = new HashMap<>();
    }

    int getId() {
        return this.id;
    }

    int getDoorCount() {
        return (int) roomSideMap.values()
                .stream()
                .filter(v -> v instanceof Door)
                .count();
    }

    void setSide(Orientation orientation, RoomSide roomSide) {
        Objects.requireNonNull(orientation);
        Objects.requireNonNull(roomSide);
        roomSideMap.put(orientation, roomSide);
    }

    @Override
    public String toString() {
        String name = this.getClass().getSimpleName();
        return name + "(id = " + id
                + ", sides = " + roomSideMap
                + ")";
    }
}
