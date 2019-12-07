package com.youdushufang.dp.interpreter.bool;

public class ConstantExp implements BooleanExp {

    private boolean value;

    public ConstantExp(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(Context context) {
        return value;
    }

    @Override
    public BooleanExp replace(String name, BooleanExp exp) {
        return this.copy();
    }

    @Override
    public BooleanExp copy() {
        return new ConstantExp(value);
    }
}
