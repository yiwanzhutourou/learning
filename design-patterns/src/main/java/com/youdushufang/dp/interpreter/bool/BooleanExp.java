package com.youdushufang.dp.interpreter.bool;

public interface BooleanExp {

    boolean evaluate(Context context);

    BooleanExp replace(String name, BooleanExp exp);

    BooleanExp copy();
}
