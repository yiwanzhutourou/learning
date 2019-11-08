package com.youdushufang.dp.test.abstract_factory.maze;

import com.youdushufang.dp.abstract_factory.maze.MazeGame;
import com.youdushufang.dp.abstract_factory.maze.factory.BombedMazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.EnchantedMazeFactory;
import com.youdushufang.dp.abstract_factory.maze.factory.MazeFactory;
import org.junit.jupiter.api.Test;

class MazeGameTest {

    @Test
    void testMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new MazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }

    @Test
    void testEnchantedMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new EnchantedMazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }

    @Test
    void testBombedMazeFactory() {
        MazeGame mazeGame = new MazeGame();
        MazeFactory mazeFactory = new BombedMazeFactory();
        System.out.println(mazeGame.createMaze(mazeFactory));
    }
}
