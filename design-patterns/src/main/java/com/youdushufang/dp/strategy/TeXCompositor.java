package com.youdushufang.dp.strategy;

import java.util.Arrays;
import java.util.List;

public class TeXCompositor implements Compositor {

    @Override
    public List<Integer> compose(List<Coordination> natural, List<Coordination> stretch,
                                 List<Coordination> shrink, int componentCount, int lineWidth) {
        // 实现略
        return Arrays.asList(componentCount / 2, componentCount - componentCount / 2);
    }
}
