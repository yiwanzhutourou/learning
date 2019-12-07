package com.youdushufang.dp.interpreter.bool;

public class NotExp implements BooleanExp {

    private BooleanExp exp;

    public NotExp(BooleanExp exp) {
        this.exp = exp;
    }

    @Override
    public boolean evaluate(Context context) {
        return !exp.evaluate(context);
    }

    @Override
    public BooleanExp replace(String name, BooleanExp exp) {
        return new NotExp(this.exp.replace(name, exp));
    }

    @Override
    public BooleanExp copy() {
        return new NotExp(exp.copy());
    }
}
