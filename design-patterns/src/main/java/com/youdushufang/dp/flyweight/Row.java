package com.youdushufang.dp.flyweight;

import java.util.ArrayList;
import java.util.List;

public class Row implements Glyph {

    private List<Character> children;

    private int currentPosition = -1;

    public Row() {
        this.children = new ArrayList<>();
    }

    @Override
    public void draw(GlyphContext context) {
        context.previous(currentPosition);
        for (Character child : children) {
            child.draw(context);
            context.next(1);
        }
        System.out.println();
        context.previous(children.size());
        context.next(currentPosition);
    }

    @Override
    public void setFont(Font font, GlyphContext context) {
        if (currentPosition < 0) {
            return;
        }
        // 设置一行的 font，光标移动到行首
        context.previous(currentPosition);
        context.setFont(font, children.size());
        // 设置成功后光标再移回去
        context.next(currentPosition);
    }

    @Override
    public Font getFont(GlyphContext context) {
        if (currentPosition < 0) {
            return null;
        }
        return context.getFont();
    }

    @Override
    public void next(GlyphContext context) {
        if (!hasNext(context)) {
            return;
        }
        context.next(1);
        currentPosition++;
    }

    @Override
    public boolean hasNext(GlyphContext context) {
        return currentPosition >= 0 && currentPosition < children.size() - 1;
    }

    @Override
    public void position(int position, GlyphContext context) {
        if (position < 0 || position > children.size()) {
            throw new IndexOutOfBoundsException();
        }
        context.previous(currentPosition);
        context.next(position);
        currentPosition = position;
    }

    @Override
    public Glyph current(GlyphContext context) {
        return currentPosition < 0 ? null : children.get(currentPosition);
    }

    @Override
    public void insert(Glyph glyph, GlyphContext context) {
        context.insert();
        children.add(++currentPosition, (Character) glyph);
    }

    @Override
    public void remove(GlyphContext context) {
        if (currentPosition < 0) {
            return;
        }
        context.remove();
        children.remove(currentPosition);
        if (currentPosition > 0) {
            currentPosition--;
        }
    }
}
