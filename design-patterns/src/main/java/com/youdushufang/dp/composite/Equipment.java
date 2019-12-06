package com.youdushufang.dp.composite;

import com.youdushufang.dp.visitor.Acceptable;
import com.youdushufang.dp.visitor.EquipmentVisitor;

import java.util.Iterator;

/**
 * 角色：Component 接口
 */
public interface Equipment extends Acceptable<EquipmentVisitor> {

    String getName();

    int power();

    void add(Equipment equipment);

    void remove(Equipment equipment);

    Iterator<Equipment> iterator();
}
