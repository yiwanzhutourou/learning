package com.youdushufang.dp.builder.maze;

import com.youdushufang.dp.builder.maze.models.CountingMazeBuilder;
import com.youdushufang.dp.builder.maze.models.Maze;
import com.youdushufang.dp.builder.maze.models.MazeBuilder;
import com.youdushufang.dp.builder.maze.models.StandardMazeBuilder;
import org.junit.jupiter.api.Test;

public class MazeGameTest {

    @Test
    public void testStandardMazeBuilder() {
        testMazeBuilder(new StandardMazeBuilder());
    }

    @Test
    public void testCountingMazeBuilder() {
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
