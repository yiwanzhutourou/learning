package com.youdushufang.dp.prototype.maze;

import com.youdushufang.dp.abstract_factory.maze.MazeGame;
import com.youdushufang.dp.abstract_factory.maze.factory.products.*;
import com.youdushufang.dp.constants.Orientation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MazePrototypeFactoryTest {

    @Test
    public void testSimpleMazePrototypeFactory() {
        Maze maze = new Maze();
        Room room = new Room(1);
        Wall wall = new Wall();
        Door door = new Door(room, room);
        MazePrototypeFactory mazePrototypeFactory = new MazePrototypeFactory(maze, room, wall, door);
        MazeGame mazeGame = new MazeGame();
        System.out.println(mazeGame.createMaze(mazePrototypeFactory));
    }

    @Test
    public void testBombedMazePrototypeFactory() {
        Maze maze = new Maze();
        Room room = new RoomWithABomb(1);
        Wall wall = new BombedWall();
        Door door = new Door(room, room);
        MazePrototypeFactory mazePrototypeFactory = new MazePrototypeFactory(maze, room, wall, door);
        MazeGame mazeGame = new MazeGame();
        System.out.println(mazeGame.createMaze(mazePrototypeFactory));
    }


    @Test
    public void testEnchantedMazePrototypeFactory() {
        Maze maze = new Maze();
        Room room = new EnchantedRoom(1, null);
        Wall wall = new Wall();
        Door door = new DoorNeedingSpell(room, room);
        MazePrototypeFactory mazePrototypeFactory = new MazePrototypeFactory(maze, room, wall, door);
        MazeGame mazeGame = new MazeGame();
        System.out.println(mazeGame.createMaze(mazePrototypeFactory));
    }

    @Test
    public void testMixedMazePrototypeFactory() {
        Maze maze = new Maze();
        Room room = new RoomWithABomb(1);
        Wall wall = new BombedWall();
        Door door = new DoorNeedingSpell(room, room);
        MazePrototypeFactory mazePrototypeFactory = new MazePrototypeFactory(maze, room, wall, door);
        MazeGame mazeGame = new MazeGame();
        System.out.println(mazeGame.createMaze(mazePrototypeFactory));
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        // ------------------
        // |       |        |
        // |room1 door room2|
        // |       |        |
        // ------------------
        Maze maze = new Maze();
        Room room1 = new Room(1);
        Room room2 = new Room(2);
        Wall wall = new Wall();
        Door door = new Door(room1, room2);
        room1.setSide(Orientation.NORTH, new Wall());
        room1.setSide(Orientation.EAST, door);
        room1.setSide(Orientation.SOUTH, new Wall());
        room1.setSide(Orientation.WEST, new Wall());
        room2.setSide(Orientation.NORTH, new Wall());
        room2.setSide(Orientation.EAST, new Wall());
        room2.setSide(Orientation.SOUTH, new Wall());
        room2.setSide(Orientation.WEST, door);
        maze.addRoom(room1);
        maze.addRoom(room2);

        // test maze
        Maze clonedMaze = (Maze) maze.clone();
        Assertions.assertNotSame(maze, clonedMaze);
        Assertions.assertNotSame(maze.getRoom(1), clonedMaze.getRoom(1));
        Assertions.assertNotSame(maze.getRoom(2), clonedMaze.getRoom(2));

        // test room
        Room clonedRoom1 = (Room) room1.clone();
        Assertions.assertNotSame(room1, clonedRoom1);
        Assertions.assertNotSame(room1.getRoomSideMap(), clonedRoom1.getRoomSideMap());
        Assertions.assertNotSame(room1.getSide(Orientation.NORTH), clonedRoom1.getSide(Orientation.NORTH));
        Assertions.assertNotSame(room1.getSide(Orientation.EAST), clonedRoom1.getSide(Orientation.EAST));

        // test wall
        Wall clonedWall = (Wall) wall.clone();
        Assertions.assertNotSame(wall, clonedWall);

        // test door
        Door clonedDoor = (Door) door.clone();
        Assertions.assertNotSame(door, clonedDoor);
        Assertions.assertNotSame(door.getR1(), clonedDoor.getR1());
        Assertions.assertNotSame(door.getR2(), clonedDoor.getR2());
    }
}
