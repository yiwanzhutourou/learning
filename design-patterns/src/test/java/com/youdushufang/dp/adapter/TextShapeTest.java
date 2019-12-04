package com.youdushufang.dp.adapter;

import org.junit.jupiter.api.Test;

public class TextShapeTest {

    @Test
    public void testTextShape() {
        TextView textView = new TextView("Hello, Adapter", 0, 0);
        TextShape textShape = new TextShape(textView);
        System.out.println(textShape.boundingBox());
        System.out.println(textShape.createManipulator().getClass());
        System.out.format("isEmpty? %b%n", textShape.isEmpty());
    }
}
