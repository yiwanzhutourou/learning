package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 具体产品（Concrete Product）
 */
public class EnchantedRoom extends Room {

    private Spell spell;

    public EnchantedRoom(int id, Spell spell) {
        super(id);
        this.spell = spell;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EnchantedRoom result;
        try {
            result = (EnchantedRoom) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        if (spell != null) {
            result.spell = (Spell) spell.clone();
        }
        return result;
    }

    @Override
    protected String getExtraMessages() {
        return "spell = " + spell;
    }
}
