package com.youdushufang.dp.abstract_factory.maze.factory.products;

import com.youdushufang.dp.constants.Orientation;

import java.util.Map;
import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Door extends RoomSide {

    Room r1;

    Room r2;

    public Door(Room r1, Room r2) {
        Objects.requireNonNull(r1);
        Objects.requireNonNull(r2);
        this.r1 = r1;
        this.r2 = r2;
    }

    public void initialize(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public Room getR1() {
        return r1;
    }

    public Room getR2() {
        return r2;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Door result;
        try {
            result = (Door) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.r1 = cloneRoom(r1, result);
        result.r2 = cloneRoom(r2, result);
        return result;
    }

    // 解决 Room 和 Door 的循环引用问题
    private Room cloneRoom(Room room, Door clonedDoor) throws CloneNotSupportedException {
        if (room != null) {
            Room clonedRoom = new Room(room.getId());
            Map<Orientation, RoomSide> roomSideMap = room.getRoomSideMap();
            for (Map.Entry<Orientation, RoomSide> entry : roomSideMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (this == entry.getValue()) {
                        clonedRoom.setSide(entry.getKey(), clonedDoor);
                    } else {
                        clonedRoom.setSide(entry.getKey(), (RoomSide) entry.getValue().clone());
                    }
                }
            }
            return clonedRoom;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Door(Room " + r1.getId() + ", Room " + r2.getId() + ")";
    }
}
