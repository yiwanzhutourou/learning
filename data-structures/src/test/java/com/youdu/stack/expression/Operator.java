package com.youdu.stack.expression;

enum Operator {

    PLUS("+", 0),
    MINUS("-", 0),
    MULTIPLY("*", 1),
    DIVIDE("/", 1);

    private String value;

    private int priority;

    Operator(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    String getValue() {
        return value;
    }

    int getPriority() {
        return priority;
    }

    static Operator from(char c) {
        switch (c) {
            case '+':
                return PLUS;
            case '-':
                return MINUS;
            case '*':
                return MULTIPLY;
            case '/':
                return DIVIDE;
            default:
                return null;
        }
    }
}
