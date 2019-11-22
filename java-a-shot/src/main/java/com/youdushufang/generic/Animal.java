package com.youdushufang.generic;

public class Animal {

    private String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + name;
    }
}
