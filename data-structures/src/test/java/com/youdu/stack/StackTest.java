package com.youdu.stack;

import com.youdu.stack.bracket.BracketTester;
import com.youdu.stack.expression.SimpleExpressionCalculator;
import com.youdu.stack.factory.ArrayStackFactory;
import com.youdu.stack.factory.LinkedStackFactory;
import com.youdu.stack.factory.StackFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StackTest {

    @Test
    public void testBracketTester() {
        Map<String, Boolean> testCases = new HashMap<>();
        testCases.put("{[()]}", true);
        testCases.put("{abc}[abc](abc)", true);
        testCases.put("{[ cde ]} ( { [(]} )", false);
        testCases.put("{", false);
        testCases.put("{}[](){{{{}}}", false);
        testCases.put("{}[](){{{{}}}}", true);
        testCases.put("", true);

        // test array stack
        BracketTester tester1 = new BracketTester(new ArrayStackFactory());
        testCases.forEach((k, v) -> Assertions.assertEquals(v, tester1.test(k)));

        // test linked stack
        BracketTester tester2 = new BracketTester(new LinkedStackFactory());
        testCases.forEach((k, v) -> Assertions.assertEquals(v, tester2.test(k)));
    }

    @Test
    public void testSimpleExpressionCalculator() {
        Map<String, Double> testCases = new HashMap<>();
        testCases.put("3.0 + 5 * 8 - 6", 37.0);
        testCases.put("3.0 + 0 * 10000.321223214", 3.0);
        testCases.put("4 - 8 + 50 * 3", 146.0);
        testCases.put("4 - 8 + 50 / 0.1 - 5000 * 0.1", -4.0);

        // test array stack
        SimpleExpressionCalculator calculator1 = new SimpleExpressionCalculator(new ArrayStackFactory());
        testCases.forEach((k, v) -> Assertions.assertEquals(v, calculator1.calculate(k)));

        // test linked stack
        SimpleExpressionCalculator calculator2 = new SimpleExpressionCalculator(new LinkedStackFactory());
        testCases.forEach((k, v) -> Assertions.assertEquals(v, calculator2.calculate(k)));
    }

    @Test
    public void testStackOperations() {
        testStackOperations(new ArrayStackFactory());
        testStackOperations(new LinkedStackFactory());
    }

    private void testStackOperations(StackFactory stackFactory) {
        Stack<Integer> stack = stackFactory.newStack(2);
        Assertions.assertNull(stack.pop());
        Assertions.assertNull(stack.peak());
        Assertions.assertTrue(stack.push(3));
        Assertions.assertTrue(stack.push(2));
        Assertions.assertTrue(stack.push(1));
        Assertions.assertEquals(3, stack.size());
        Assertions.assertEquals(1, stack.peak());
        Assertions.assertEquals(1, stack.pop());
        Assertions.assertEquals(2, stack.pop());
        Assertions.assertEquals(3, stack.pop());
        Assertions.assertEquals(0, stack.size());
    }
}
