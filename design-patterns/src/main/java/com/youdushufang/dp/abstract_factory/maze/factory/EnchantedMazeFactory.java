package com.youdushufang.dp.abstract_factory.maze.factory;

import com.youdushufang.dp.abstract_factory.maze.factory.products.*;

/**
 * 角色：具体工厂（Concrete Factory）
 *
 * 可以创建"魔法房间"以及"需要施法的门"两个产品，"迷宫"和"墙"继承自默认实现
 */
public class EnchantedMazeFactory extends MazeFactory {

    @Override
    public Room makeRoom(int id) {
        return new EnchantedRoom(id, castSpell());
    }

    @Override
    public Door makeDoor(Room r1, Room r2) {
        return new DoorNeedingSpell(r1, r2);
    }

    private Spell castSpell() {
        return new Spell("random spell");
    }
}
