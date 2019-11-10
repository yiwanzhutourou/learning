package com.youdushufang.dp.test.builder;

import com.youdushufang.dp.builder.maze.MazeGame;
import com.youdushufang.dp.builder.maze.models.CountingMazeBuilder;
import com.youdushufang.dp.builder.maze.models.Maze;
import com.youdushufang.dp.builder.maze.models.MazeBuilder;
import com.youdushufang.dp.builder.maze.models.StandardMazeBuilder;
import org.junit.jupiter.api.Test;

class MazeGameTest {

    @Test
    void testStandardMazeBuilder() {
        testMazeBuilder(new StandardMazeBuilder());
    }

    @Test
    void testCountingMazeBuilder() {
        testMazeBuilder(new CountingMazeBuilder());
    }

    private void testMazeBuilder(MazeBuilder mazeBuilder) {
        MazeGame mazeGame = new MazeGame();
        Maze maze = mazeGame.createMaze(mazeBuilder);
        System.out.println(maze);
        System.out.println("room count: " + mazeBuilder.getRoomCount());
        System.out.println("door count: " + mazeBuilder.getDoorCount());
    }
}
