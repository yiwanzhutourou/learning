package com.youdushufang.dp.flyweight;

import java.util.HashMap;
import java.util.Map;

public class GlyphFactory {

    private static volatile GlyphFactory INSTANCE;

    private Map<Integer, Character> characterMap;

    private GlyphFactory() {
        characterMap = new HashMap<>();
    }

    public Row createRow() {
        return new Row();
    }

    public Character createCharacter(char c) {
        Character character = characterMap.get((int) c);
        if (character == null) {
            character = new Character(c);
            characterMap.put((int) c, character);
        }
        return character;
    }

    public static GlyphFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlyphFactory();
        }
        return INSTANCE;
    }
}
