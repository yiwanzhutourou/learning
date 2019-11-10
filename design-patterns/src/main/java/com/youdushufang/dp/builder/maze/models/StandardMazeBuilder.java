package com.youdushufang.dp.builder.maze.models;

import com.youdushufang.dp.constants.Orientation;

import java.util.Objects;

public class StandardMazeBuilder implements MazeBuilder {

    private Maze currentMaze;

    @Override
    public MazeBuilder buildMaze() {
        currentMaze = new Maze();
        return this;
    }

    @Override
    public MazeBuilder buildRoom(int id) {
        Objects.requireNonNull(currentMaze, "Maze not initialized");
        if (currentMaze.getRoom(id) == null) {
            Room room = new Room(id);
            currentMaze.addRoom(room);
            room.setSide(Orientation.NORTH, new Wall());
            room.setSide(Orientation.EAST, new Wall());
            room.setSide(Orientation.SOUTH, new Wall());
            room.setSide(Orientation.WEST, new Wall());
        }
        return this;
    }

    @Override
    public MazeBuilder buildDoor(int roomFrom, int roomTo) {
        Room r1 = Objects.requireNonNull(currentMaze.getRoom(roomFrom));
        Room r2 = Objects.requireNonNull(currentMaze.getRoom(roomTo));
        if (!isAdjacent(r1, r2)) {
            throw new IllegalArgumentException("Rooms not adjacent");
        }
        Door door = new Door(r1, r2);
        r1.setSide(commonWall(r1, r2), door);
        r2.setSide(commonWall(r2, r1), door);
        return this;
    }

    @Override
    public Maze getMaze() {
        return currentMaze;
    }

    @Override
    public int getRoomCount() {
        return currentMaze.getRoomCount();
    }

    @Override
    public int getDoorCount() {
        return currentMaze.getDoorCount();
    }

    /**
     * 返回房间 r1 与房间 r2 相邻的墙位于房间 r1 的什么方位
     * **简化问题，假设这是一个 1 * n 的迷宫，房间从左到右依次排列
     */
    private Orientation commonWall(Room r1, Room r2) {
        int distance = r1.getId() - r2.getId();
        return distance == -1
                ? Orientation.EAST
                : (distance == 1 ? Orientation.WEST : null);
    }

    /**
     * 返回房间 r1 与房间 r2 是否相邻
     * **简化问题，假设这是一个 1 * n 的迷宫，房间从左到右依次排列
     */
    private boolean isAdjacent(Room r1, Room r2) {
        int distance = r1.getId() - r2.getId();
        return distance == 1 || distance == -1;
    }
}
