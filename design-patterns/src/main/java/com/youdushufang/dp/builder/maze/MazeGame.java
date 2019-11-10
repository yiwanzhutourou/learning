package com.youdushufang.dp.builder.maze;

import com.youdushufang.dp.builder.maze.models.Maze;
import com.youdushufang.dp.builder.maze.models.MazeBuilder;

public class MazeGame {

    // 选择了什么样的构建器，我就能得到什么样的迷宫，我只需要关心具体的构建步骤的就可以了
    public Maze createMaze(MazeBuilder mazeBuilder) {
        return mazeBuilder.buildMaze()
                .buildRoom(1)
                .buildRoom(2)
                .buildRoom(3)
                .buildRoom(4)
                .buildRoom(5)
                .buildDoor(1, 2)
                .buildDoor(3, 4)
                .buildDoor(4, 5)
                .getMaze();
    }
}
