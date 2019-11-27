package com.youdushufang.dp.proxy;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Event {

    private int type;

    private Point where;

    public int getType() {
        return type;
    }

    public Event setType(int type) {
        this.type = type;
        return this;
    }

    public Point getWhere() {
        return where;
    }

    public Event setWhere(Point where) {
        this.where = where;
        return this;
    }
}
