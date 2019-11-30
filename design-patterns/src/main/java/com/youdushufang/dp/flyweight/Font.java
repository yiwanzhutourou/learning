package com.youdushufang.dp.flyweight;

public enum Font {
    COURIER("Courier"),
    DROID("Droid"),
    FIRA("Fira"),
    MENLO("Menlo"),
    MONO("Mono"),
    OSAKA("Osaka");

    private String name;

    Font(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
