package com.youdushufang.dp.strategy;

import lombok.Data;

@Data
public class Coordination {

    private int x;

    private int y;

    public Coordination(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
