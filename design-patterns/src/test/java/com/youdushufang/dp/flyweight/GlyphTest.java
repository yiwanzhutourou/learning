package com.youdushufang.dp.flyweight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GlyphTest {

    @Test
    public void testRow() {
        GlyphFactory glyphFactory = GlyphFactory.getInstance();
        GlyphContext context = new GlyphContext();

        Row row = glyphFactory.createRow();
        row.insert(glyphFactory.createCharacter('a'), context);
        row.insert(glyphFactory.createCharacter('b'), context);
        row.insert(glyphFactory.createCharacter('c'), context);
        row.insert(glyphFactory.createCharacter('d'), context);
        row.insert(glyphFactory.createCharacter('e'), context);

        row.setFont(Font.FIRA, context);
        row.draw(context);

        row.position(0, context);
        row.current(context).setFont(Font.MONO, context);
        row.insert(glyphFactory.createCharacter('f'), context);
        row.insert(glyphFactory.createCharacter('g'), context);
        row.next(context);
        row.next(context);
        row.current(context).setFont(Font.OSAKA, context);
        row.draw(context);

        row.remove(context);
        row.remove(context);
        row.draw(context);

        row.next(context);
        row.current(context).setFont(Font.DROID, context);
        row.draw(context);
    }

    @Test
    public void testFlyweight() {
        GlyphFactory glyphFactory = GlyphFactory.getInstance();
        Character c1 = glyphFactory.createCharacter('a');
        Character c2 = glyphFactory.createCharacter('a');
        Assertions.assertSame(c1, c2);
    }
}
