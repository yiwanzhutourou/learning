package com.youdushufang.dp.interpreter.bool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanExpTest {

    @Test
    public void testBooleanEvaluate() {
        BooleanExp expression;
        Context context = new Context();

        VariableExp a = new VariableExp("a");
        VariableExp b = new VariableExp("b");

        // (true && a) || (b && !a), a = false, b = true
        expression = new OrExp(
                new AndExp(new ConstantExp(true), a),
                new AndExp(b, new NotExp(a))
        );
        context.assign(a, false);
        context.assign(b, true);
        Assertions.assertTrue(expression.evaluate(context));

        VariableExp c = new VariableExp("c");
        NotExp notC = new NotExp(c);
        // (true && a) || (!c && !a), a = false, c = true
        BooleanExp replacement = expression.replace("b", notC);
        context.assign(c, true);
        Assertions.assertFalse(replacement.evaluate(context));

        BooleanExp copy = expression.copy();
        Assertions.assertTrue(copy.evaluate(context));
    }
}
