package com.youdushufang.dp.flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * 维护字体上下文
 */
public class GlyphContext {

    private static final Font DEFAULT_FONT = Font.COURIER;

    private int index;

    // 书里的例子是用一棵树来存字体的，这里简化为用一个数组来存每一个字符的字体，
    // 这里主要是为了实现享元模式，数据结构不是重点，不过文本编辑器的数据结构应该是怎样的？
    // 感觉上可以用 Skip List 来达到和树一样的效果，具体没有深入研究。
    private List<Font> fonts;

    public GlyphContext() {
        this.index = 0;
        this.fonts = new ArrayList<>();
    }

    public void previous(int step) {
        index -= step;
        if (index < 0) {
            index = 0;
        }
    }

    public void next(int step) {
        index += step;
        if (index >= fonts.size()) {
            index = Math.max(0, fonts.size() - 1);
        }
    }

    /**
     * 在当前光标位置后增加一个字符
     */
    public void insert() {
        next(1);
        fonts.add(index, DEFAULT_FONT);
    }

    /**
     * 删除当前光标位置前的一个字符，光标自然向前移动一个字符
     */
    public void remove() {
        fonts.remove(index);
        if (index > 0) {
            index--;
        }
    }

    /**
     * 获取当前位置对应的字体
     * @return 当前位置对应的字体
     */
    public Font getFont() {
        return fonts.get(index);
    }

    /**
     * 从当前位置开始将 span 个字符的字体设置为 font
     * @param font 需要设置的字体
     * @param span 字符个数
     */
    public void setFont(Font font, int span) {
        int end = Math.min(index + span, fonts.size());
        for (int i = index; i < end; i++) {
            fonts.set(i, font);
        }
    }
}
