package com.youdushufang.dp.strategy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CompositionTest {

    @Test
    public void testCompositor() {
        List<String> text = Arrays.asList(
                "Hello", "world,", "Strategy", "pattern", "rocks."
        );
        Composition composition = new Composition(
                new SimpleCompositor(), text, 10
        );
        composition.repair();
        composition.setCompositor(new ArrayCompositor());
        composition.repair();
        composition.setCompositor(new TeXCompositor());
        composition.repair();
    }
}
