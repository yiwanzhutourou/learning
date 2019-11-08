package com.youdushufang.dp.abstract_factory.maze.factory.products;

/**
 * 无关角色，作为"魔法房间"的一个属性
 */
public class Spell {

    private String content;

    public Spell(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Spell(" + content + ")";
    }
}
