package com.youdushufang.dp.flyweight;

public interface Glyph {

    void draw(GlyphContext context);

    default void setFont(Font font, GlyphContext context) { };

    default Font getFont(GlyphContext context) { return null; };

    default void next(GlyphContext context) { };

    default boolean hasNext(GlyphContext context) { return false; };

    default void position(int position, GlyphContext context) { };

    default Glyph current(GlyphContext context) { return null; };

    default void insert(Glyph glyph, GlyphContext context) { };

    default void remove(GlyphContext context) { };
}
