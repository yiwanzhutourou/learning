package com.youdushufang.dp.factory_method.maze;

import com.youdushufang.dp.abstract_factory.maze.factory.products.BombedWall;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Room;
import com.youdushufang.dp.abstract_factory.maze.factory.products.RoomWithABomb;
import com.youdushufang.dp.abstract_factory.maze.factory.products.Wall;

/**
 * 角色：ConcreteCreator
 */
public class BombedMazeGame extends MazeGame {

    @Override
    protected Wall makeWall() {
        return new BombedWall();
    }

    @Override
    protected Room makeRoom(int n) {
        return new RoomWithABomb(n);
    }
}
