package com.youdu.stack.bracket;

import com.youdu.stack.Stack;
import com.youdu.stack.factory.StackFactory;

public class BracketTester {

    private StackFactory stackFactory;

    public BracketTester(StackFactory stackFactory) {
        this.stackFactory = stackFactory;
    }

    public boolean test(String expression) {
        if (expression == null || expression.length() == 0) {
            return true;
        }
        Stack<Character> stack = stackFactory.newStack(8);
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isLeftBracket(c)) {
                stack.push(c);
            } else if (isRightBracket(c)) {
                Character left = stack.pop();
                if (left == null || !match(left, c)) {
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    private boolean isLeftBracket(char c) {
        return '(' == c || '[' == c || '{' == c;
    }

    private boolean isRightBracket(char c) {
        return ')' == c || ']' == c || '}' == c;
    }

    private boolean match(char left, char right) {
        switch (left) {
            case '(':
                return right == ')';
            case '[':
                return right == ']';
            case '{':
                return right == '}';
            default:
                return false;
        }
    }
}
