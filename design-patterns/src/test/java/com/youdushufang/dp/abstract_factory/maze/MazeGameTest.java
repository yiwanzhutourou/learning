package com.youdushufang.dp.abstract_factory.maze;

import com.youdushufang.dp.abstract_factory.maze.factory.BombedMazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.EnchantedMazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.MazeFactory;
import org.junit.jupiter.api.Test;

public class MazeGameTest {

    @Test
    public void testMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new MazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }

    @Test
    public void testEnchantedMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new EnchantedMazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }

    @Test
    public void testBombedMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new BombedMazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }
}
