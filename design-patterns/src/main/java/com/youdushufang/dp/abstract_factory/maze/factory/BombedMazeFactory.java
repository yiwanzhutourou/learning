package com.youdushufang.dp.abstract_factory.maze.factory;

import com.youdushufang.dp.abstract_factory.maze.factory.products.*;

/**
 * 角色：具体工厂（Concrete Factory）
 *
 * 可以创建"可以炸掉的墙"以及"有炸弹的房间"两个产品，"迷宫"和"门"继承自默认实现
 */
public class BombedMazeFactory extends MazeFactory {

    @Override
    public Wall makeWall() {
        return new BombedWall();
    }

    @Override
    public Room makeRoom(int id) {
        return new RoomWithABomb(id);
    }
}
