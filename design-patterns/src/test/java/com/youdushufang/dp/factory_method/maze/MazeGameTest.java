package com.youdushufang.dp.factory_method.maze;

import org.junit.jupiter.api.Test;

public class MazeGameTest {

    @Test
    public void testBombedMazeGame() {
        MazeGame mazeGame = new BombedMazeGame();
        System.out.println(mazeGame.createMaze());
    }

    @Test
    public void testEnchantedMazeGame() {
        MazeGame mazeGame = new EnchantedMazeGame();
        System.out.println(mazeGame.createMaze());
    }
}
