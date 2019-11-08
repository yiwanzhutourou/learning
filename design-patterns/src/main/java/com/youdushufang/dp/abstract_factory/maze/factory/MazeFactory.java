package com.youdushufang.dp.abstract_factory.maze.factory;

import com.youdushufang.dp.abstract_factory.maze.factory.products.Door;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Maze;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Room;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Wall;

/**
 * 角色：抽象工厂（Abstract Factory），由于这里的实现不是一个抽象类，所以这也是一个具体工厂（Concrete Factory），
 * 既是抽象工厂，也是具体工厂，以此来提供一个抽象工厂的默认实现
 */
public class MazeFactory {

    public Maze makeMaze() {
        return new Maze();
    }

    public Room makeRoom(int id) {
        return new Room(id);
    }

    public Wall makeWall() {
        return new Wall();
    }

    public Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }
}
