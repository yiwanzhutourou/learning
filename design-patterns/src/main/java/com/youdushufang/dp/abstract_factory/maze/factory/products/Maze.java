package com.youdushufang.dp.abstract_factory.maze.factory.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Maze implements Cloneable {

    private List<Room> rooms;

    public Maze() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        Objects.requireNonNull(room);
        rooms.add(room);
    }

    public Room getRoom(int roomId) {
        for (Room room : rooms) {
            if (roomId == room.getId()) {
                return room;
            }
        }
        return null;
    }

    public void initialize() {
        this.rooms.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Maze result;
        try {
            result = (Maze) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.rooms = new ArrayList<>();
        copyRooms(result);
        return result;
    }

    private void copyRooms(Maze maze) throws CloneNotSupportedException {
        for (Room room : rooms) {
            maze.addRoom((Room) room.clone());
        }
    }

    @Override
    public String toString() {
        return "Maze(rooms = " + rooms + ")";
    }
}
