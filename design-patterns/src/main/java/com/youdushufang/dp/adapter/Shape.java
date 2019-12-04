package com.youdushufang.dp.adapter;

/**
 * 角色：Target
 */
public interface Shape {

    Rectangle boundingBox();

    Manipulator createManipulator();
}
