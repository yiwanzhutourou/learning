package com.youdushufang.dp.interpreter.bool;

public class AndExp implements BooleanExp {

    private BooleanExp left;

    private BooleanExp right;

    public AndExp(BooleanExp left, BooleanExp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(Context context) {
        return left.evaluate(context) && right.evaluate(context);
    }

    @Override
    public BooleanExp replace(String name, BooleanExp exp) {
        return new AndExp(left.replace(name, exp), right.replace(name, exp));
    }

    @Override
    public BooleanExp copy() {
        return new AndExp(left.copy(), right.copy());
    }
}
