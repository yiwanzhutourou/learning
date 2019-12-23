package com.youdu.stack.expression;

import com.youdu.stack.Stack;
import com.youdu.stack.factory.StackFactory;

public class SimpleExpressionCalculator {

    private StackFactory stackFactory;

    public SimpleExpressionCalculator(StackFactory stackFactory) {
        this.stackFactory = stackFactory;
    }

    public double calculate(CharSequence expression) {
        if (expression == null || expression.length() == 0) {
            throw new UnsupportedOperationException();
        }
        // 结果栈和运算符栈
        Stack<Double> resultStack = stackFactory.newStack(16);
        Stack<Operator> operatorStack = stackFactory.newStack(16);
        // 当前的数字
        StringBuilder currentNumber = new StringBuilder();
        Operator currentOperator;
        // 遍历输入的表达式：1. 遇到数字或小数点就持续累；
        // 2. 遇到运算符就停止累加进行栈操作；
        // 3. 忽略空格；
        // 4. 遇到其他符号都报错
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                currentNumber.append(c);
            } else if ((currentOperator = Operator.from(c)) != null) {
                operateStackElements(resultStack, operatorStack, fromString(currentNumber), currentOperator);
                currentNumber.delete(0, currentNumber.length());
            } else if (!Character.isWhitespace(c)) {
                throw new UnsupportedOperationException();
            }
        }
        operateStackElements(resultStack, operatorStack, fromString(currentNumber), null);
        return resultStack.pop();
    }

    // 操作栈：如果运算符栈顶元素的优先级高，就将当前运算符压入栈；
    // 如果比运算符栈顶元素的优先级低或者相同，从运算符栈中取栈顶运算符，
    // 从操作数栈的栈顶取 2 个操作数，然后进行计算，再把计算完的结果压入操作数栈，继续比较。
    private void operateStackElements(Stack<Double> resultStack, Stack<Operator> operatorStack,
                                      double currentNumber, Operator currentOperator) {
        resultStack.push(currentNumber);
        Operator topOperator = operatorStack.peak();
        if (currentOperator == null
                || (topOperator != null && currentOperator.getPriority() <= topOperator.getPriority())) {
            calculateStackElements(resultStack, operatorStack);
        }
        operatorStack.push(currentOperator);
    }

    // 结合结果栈中的数字和运算符栈中的运算符，将计算结果层层计算后压入结果栈
    private void calculateStackElements(Stack<Double> resultStack, Stack<Operator> operatorStack) {
        while (operatorStack.size() > 0) {
            Operator operator = operatorStack.pop();
            Double d2 = resultStack.pop();
            Double d1 = resultStack.pop();
            if (d1 == null || d2 == null) {
                throw new UnsupportedOperationException();
            }
            resultStack.push(calculate(d1, d2, operator));
        }

    }

    private double calculate(double d1, double d2, Operator operator) {
        switch (operator) {
            case PLUS:
                return d1 + d2;
            case MINUS:
                return d1 - d2;
            case MULTIPLY:
                return d1 * d2;
            case DIVIDE:
                if (d2 == 0) {
                    throw new UnsupportedOperationException();
                }
                return d1 / d2;
            default:
                throw new UnsupportedOperationException();
        }
    }


    private double fromString(StringBuilder number) {
        return Double.parseDouble(number.toString());
    }
}
