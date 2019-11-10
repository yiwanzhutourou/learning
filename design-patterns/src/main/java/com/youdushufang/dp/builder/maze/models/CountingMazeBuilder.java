package com.youdushufang.dp.builder.maze.models;

/**
 * Builder 实际上对调用者隐藏了对象的构造方式，例如这个 CountingMazeBuilder 只提供计数
 * 功能，实际上它根本就没有创建 Maze
 */
public class CountingMazeBuilder implements MazeBuilder {

    private int roomCount;

    private int doorCount;

    @Override
    public MazeBuilder buildMaze() {
        // do nothing
        return this;
    }

    @Override
    public MazeBuilder buildRoom(int room) {
        roomCount++;
        return this;
    }

    @Override
    public MazeBuilder buildDoor(int roomFrom, int roomTo) {
        doorCount++;
        return this;
    }

    @Override
    public Maze getMaze() {
        return null;
    }

    @Override
    public int getRoomCount() {
        return roomCount;
    }

    @Override
    public int getDoorCount() {
        return doorCount;
    }
}
