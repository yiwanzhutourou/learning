package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 定义可以作为房间一边的接口
 */
abstract class RoomSide implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
