package com.youdushufang.dp.factory_method.maze;

import com.youdushufang.dp.abstract_factory.maze.factory.products.Door;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Maze;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Room;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Wall;
import com.youdushufang.dp.constants.Orientation;

/**
 * 角色：Creator
 */
public abstract class MazeGame {

    // Creator 包含了调用工厂方法的逻辑，创建一个迷宫
    public Maze createMaze() {
        Maze maze = makeMaze();

        Room r1 = makeRoom(1);
        Room r2 = makeRoom(2);
        Door door = makeDoor(r1, r2);
        maze.addRoom(r1);
        maze.addRoom(r2);

        r1.setSide(Orientation.NORTH, makeWall());
        r1.setSide(Orientation.EAST, door);
        r1.setSide(Orientation.SOUTH, makeWall());
        r1.setSide(Orientation.WEST, makeWall());

        r2.setSide(Orientation.NORTH, makeWall());
        r2.setSide(Orientation.EAST, makeWall());
        r2.setSide(Orientation.SOUTH, makeWall());
        r2.setSide(Orientation.WEST, door);

        return maze;
    }

    // 下面是 4 个工厂方法，且提供了默认实现
    protected Maze makeMaze() {
        return new Maze();
    }

    protected Room makeRoom(int n) {
        return new Room(n);
    }

    protected Wall makeWall() {
        return new Wall();
    }

    protected Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }
}
