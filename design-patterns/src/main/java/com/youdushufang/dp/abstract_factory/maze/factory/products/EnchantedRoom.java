package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 具体产品（Concrete Product）
 */
public class EnchantedRoom extends Room {

    private final Spell spell;

    public EnchantedRoom(int id, Spell spell) {
        super(id);
        this.spell = spell;
    }

    @Override
    protected String getExtraMessages() {
        return "spell = " + spell;
    }
}
