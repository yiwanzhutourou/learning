package com.youdushufang.dp.abstract_factory.maze.factory.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Maze {

    private List<Room> rooms;

    public Maze() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        Objects.requireNonNull(room);
        rooms.add(room);
    }

    @Override
    public String toString() {
        return "Maze(rooms = " + rooms + ")";
    }
}
