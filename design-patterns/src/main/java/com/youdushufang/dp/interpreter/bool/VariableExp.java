package com.youdushufang.dp.interpreter.bool;

import java.util.Objects;

public class VariableExp implements BooleanExp {

    private String name;

    public VariableExp(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public boolean evaluate(Context context) {
        return context.lookup(name);
    }

    @Override
    public BooleanExp replace(String name, BooleanExp exp) {
        if (Objects.equals(this.name, name)) {
            return exp.copy();
        } else {
            return new VariableExp(this.name);
        }
    }

    @Override
    public BooleanExp copy() {
        return new VariableExp(name);
    }

    public String getName() {
        return name;
    }
}
