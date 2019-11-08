package com.youdushufang.dp.abstract_factory.maze.factory.products;

import com.youdushufang.dp.abstract_factory.maze.constants.Orientation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Room {

    private int id;

    private Map<Orientation, RoomSide> roomSideMap;

    public Room(int id) {
        this.id = id;
        this.roomSideMap = new HashMap<>();
    }

    int getId() {
        return this.id;
    }

    public void setSide(Orientation orientation, RoomSide roomSide) {
        Objects.requireNonNull(orientation);
        Objects.requireNonNull(roomSide);
        roomSideMap.put(orientation, roomSide);
    }

    protected String getExtraMessages() {
        return "";
    }

    @Override
    public String toString() {
        String name = this.getClass().getSimpleName();
        String extraMessages = getExtraMessages();
        return name + "(id = " + id
                + ", sides = " + roomSideMap
                + ((extraMessages != null && !"".equals(extraMessages)) ? ", " + extraMessages :"")
                + ")";
    }
}
