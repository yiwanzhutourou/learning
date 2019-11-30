package com.youdushufang.dp.flyweight;

public class Character implements Glyph {

    private final char c;

    public Character(char c) {
        this.c = c;
    }

    @Override
    public void draw(GlyphContext context) {
        Font font = context.getFont();
        System.out.print(c + "(" + font.getName() + ")");
    }

    @Override
    public void setFont(Font font, GlyphContext context) {
        context.setFont(font, 1);
    }
}
