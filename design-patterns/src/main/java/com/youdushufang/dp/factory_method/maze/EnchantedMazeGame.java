package com.youdushufang.dp.factory_method.maze;

import com.youdushufang.dp.abstract_factory.maze.factory.products.*;

/**
 * 角色：ConcreteCreator
 */
public class EnchantedMazeGame extends MazeGame {

    @Override
    protected Room makeRoom(int n) {
        return new EnchantedRoom(n, castSpell());
    }

    @Override
    protected Door makeDoor(Room r1, Room r2) {
        return new DoorNeedingSpell(r1, r2);
    }

    private Spell castSpell() {
        return new Spell("random spell");
    }
}
