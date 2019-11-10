package com.youdushufang.dp.builder.maze.models;

/**
 * 定义迷宫构建者接口，把大象放进冰箱总共要几步？
 */
public interface MazeBuilder {

    // 第一步，构建迷宫，鬼知道你是怎么构建的，但是你不需要关心
    MazeBuilder buildMaze();

    // 第二步，构建一些房间
    MazeBuilder buildRoom(int room);

    // 第三步，在一些房间之间开门
    MazeBuilder buildDoor(int roomFrom, int roomTo);

    // 获取最终构建成功的迷宫
    Maze getMaze();

    // 下面是一些构建相关的统计函数
    int getRoomCount();

    int getDoorCount();
}
