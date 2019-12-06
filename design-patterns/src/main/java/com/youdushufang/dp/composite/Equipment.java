package com.youdushufang.dp.composite;

import java.util.Iterator;

/**
 * 角色：Component 接口
 */
public interface Equipment {

    String getName();

    int power();

    void add(Equipment equipment);

    void remove(Equipment equipment);

    Iterator<Equipment> iterator();
}
