package com.youdushufang.dp.strategy;

import java.util.Collections;
import java.util.List;

public class SimpleCompositor implements Compositor {
    @Override
    public List<Integer> compose(List<Coordination> natural, List<Coordination> stretch,
                                 List<Coordination> shrink, int componentCount, int lineWidth) {
        // 实现略
        return Collections.singletonList(componentCount);
    }
}
