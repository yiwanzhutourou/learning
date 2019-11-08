package com.youdushufang.dp.abstract_factory.maze;

import com.youdushufang.dp.abstract_factory.maze.constants.Orientation;
import com.youdushufang.dp.abstract_factory.maze.factory.MazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Door;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Maze;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Room;

/**
 * 角色：Client
 *
 * 由于运用了抽象工厂模式，达到了以下目的：
 * 1. 创建迷宫时完全不需要关心具体的迷宫的"样式"，而只需要关心如何搭配迷宫组件
 * 2. 替换不同的迷宫工厂，就可以更换迷宫的样式
 * 3. 由迷宫工厂来限定不同的迷宫组件间的搭配，这样就不会出现一个"魔法房间"搭配"炸弹墙的情况"
 *
 * 书里的这个例子似乎举得不够恰当，抽象工厂本意是为 Client 提供一组创建一系列关联"产品"的接口，
 * 不同的具体工厂的产品之间应该是无关联的。例如，一款图形界面的编辑器可以有不同样式的外观，对于
 * 一种具体的外观样式，窗口、按钮、滚动条的样式应该是统一的，而不同样式的组件之间应是无关的，具体
 * 工厂应该负责提供样式一致的创建接口。
 *
 * 对于迷宫这个例子来说，如果让我设计一个迷宫游戏的话，迷宫里的组件应该是可以灵活组合的，无所谓
 * "风格统一"一说，"有炸弹的房间"搭配"魔法房间"组成一个迷宫也是有可能的。
 */
public class MazeGame {

    /**
     * 创建一个迷宫
     *
     * @param mazeFactory 迷宫工厂
     * @return 创建成功的迷宫对象
     */
    public Maze createMaze(MazeFactory mazeFactory) {
        // 初始化迷宫
        Maze maze = mazeFactory.makeMaze();

        Room r1 = mazeFactory.makeRoom(1);
        Room r2 = mazeFactory.makeRoom(2);
        Door door = mazeFactory.makeDoor(r1, r2);
        maze.addRoom(r1);
        maze.addRoom(r2);

        r1.setSide(Orientation.NORTH, mazeFactory.makeWall());
        r1.setSide(Orientation.EAST, door);
        r1.setSide(Orientation.SOUTH, mazeFactory.makeWall());
        r1.setSide(Orientation.WEST, mazeFactory.makeWall());

        r2.setSide(Orientation.NORTH, mazeFactory.makeWall());
        r2.setSide(Orientation.EAST, mazeFactory.makeWall());
        r2.setSide(Orientation.SOUTH, mazeFactory.makeWall());
        r2.setSide(Orientation.WEST, door);

        return maze;
    }
}
