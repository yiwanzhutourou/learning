package com.youdushufang.dp.strategy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayCompositor implements Compositor {

    @Override
    public List<Integer> compose(List<Coordination> natural, List<Coordination> stretch,
                                 List<Coordination> shrink, int componentCount, int lineWidth) {
        // 实现略
        return Stream.generate(() -> 1)
                .limit(componentCount)
                .collect(Collectors.toList());
    }
}
