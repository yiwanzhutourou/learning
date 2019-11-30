package com.youdushufang.dp.strategy;

import java.util.List;

public interface Compositor {

    List<Integer> compose(List<Coordination> natural, List<Coordination> stretch,
                          List<Coordination> shrink, int componentCount, int lineWidth);
}
