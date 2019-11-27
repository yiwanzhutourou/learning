package com.youdushufang.dp.abstract_factory.maze.factory.products;

import com.youdushufang.dp.constants.Orientation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Room implements Cloneable {

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

    public RoomSide getSide(Orientation orientation) {
        Objects.requireNonNull(orientation);
        return roomSideMap.get(orientation);
    }

    public void initialize(int id) {
        this.id = id;
        this.roomSideMap.clear();
    }

    protected String getExtraMessages() {
        return "";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Room result;
        try {
            result = (Room) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.roomSideMap = new HashMap<>();
        copyRoomSide(result);
        return result;
    }

    private void copyRoomSide(Room room) throws CloneNotSupportedException {
        for (Map.Entry<Orientation, RoomSide> entry : roomSideMap.entrySet()) {
            room.setSide(entry.getKey(), (RoomSide) entry.getValue().clone());
        }
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

    public Map<Orientation, RoomSide> getRoomSideMap() {
        return roomSideMap;
    }
}
