package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 角色：抽象产品（Concrete Factory），以及作为默认实现的具体产品（Concrete Product）
 */
public class Wall extends RoomSide {

    @Override
    public String toString() {
        return "Wall";
    }
}
