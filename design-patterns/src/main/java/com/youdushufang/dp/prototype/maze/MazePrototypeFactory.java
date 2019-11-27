package com.youdushufang.dp.prototype.maze;

import com.youdushufang.dp.abstract_factory.maze.factory.MazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Door;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Maze;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Room;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Wall;

/**
 * 在抽象工厂模式中，通过继承抽象工厂 MazeFactory 来构造具体的产品；在原型模式
 * 中，原型工厂 MazePrototypeFactory 通过拷贝原型提供不同的产品
 */
public class MazePrototypeFactory extends MazeFactory {

    private Maze prototypeMaze;

    private Room prototypeRoom;

    private Wall prototypeWall;

    private Door prototypeDoor;

    public MazePrototypeFactory(Maze maze, Room room,
                                Wall wall, Door door) {
        this.prototypeMaze = maze;
        this.prototypeRoom = room;
        this.prototypeWall = wall;
        this.prototypeDoor = door;
    }

    @Override
    public Maze makeMaze() {
        Maze maze;
        try {
            maze = (Maze) prototypeMaze.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        maze.initialize();
        return maze;
    }

    @Override
    public Room makeRoom(int id) {
        Room room;
        try {
            room = (Room) prototypeRoom.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        room.initialize(id);
        return room;
    }

    @Override
    public Wall makeWall() {
        Wall wall;
        try {
            wall = (Wall) prototypeWall.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        return wall;
    }

    @Override
    public Door makeDoor(Room r1, Room r2) {
        Door door;
        try {
            door = (Door) prototypeDoor.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        door.initialize(r1, r2);
        return door;
    }
}
