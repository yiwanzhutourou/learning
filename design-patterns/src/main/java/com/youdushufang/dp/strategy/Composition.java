package com.youdushufang.dp.strategy;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Composition {

    private Compositor compositor;

    private List<String> components;

    private int lineWidth;

    public Composition(Compositor compositor, List<String> components, int lineWidth) {
        this.compositor = compositor;
        this.components = components;
        this.lineWidth = lineWidth;
    }

    public void repair() {
        List<Integer> lineCounts = compositor.compose(
                toNaturalCoordinationList(),
                toStretchCoordinationList(),
                toShrinkCoordinationList(),
                components.size(),
                lineWidth
        );
        Iterator<String> componentIterator = components.iterator();
        for (Integer lineCount : lineCounts) {
            for (int i = 0; i < lineCount && componentIterator.hasNext(); i++) {
                System.out.print(componentIterator.next() + " ");
            }
            System.out.println();
        }
    }

    public void setCompositor(Compositor compositor) {
        this.compositor = compositor;
    }

    private List<Coordination> toNaturalCoordinationList() {
        return components.stream()
                .map(s -> new Coordination(s.length(), 1))
                .collect(Collectors.toList());
    }

    private List<Coordination> toStretchCoordinationList() {
        return components.stream()
                .map(s -> new Coordination(s.length() * 2, 1))
                .collect(Collectors.toList());
    }

    private List<Coordination> toShrinkCoordinationList() {
        return components.stream()
                .map(s -> new Coordination(s.length() / 2, 1))
                .collect(Collectors.toList());
    }
}
