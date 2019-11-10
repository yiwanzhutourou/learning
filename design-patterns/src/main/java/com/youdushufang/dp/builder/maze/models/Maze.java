package com.youdushufang.dp.builder.maze.models;

import java.util.*;

public class Maze {

    private final Map<Integer, Room> roomMap;

    Maze() {
        this.roomMap = new HashMap<>();
    }

    void addRoom(Room room) {
        Objects.requireNonNull(room);
        roomMap.put(room.getId(), room);
    }

    Room getRoom(int id) {
        return roomMap.get(id);
    }

    int getRoomCount() {
        return roomMap.size();
    }

    int getDoorCount() {
        return roomMap.values()
                .stream()
                .map(Room::getDoorCount)
                .reduce(Integer::sum)
                .orElse(0) / 2;
    }

    @Override
    public String toString() {
        return "Maze(rooms = " + roomMap + ")";
    }
}
