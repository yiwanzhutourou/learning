package com.youdushufang.dp.interpreter.bool;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, Boolean> variables = new HashMap<>();

    boolean lookup(String variableName) {
        Boolean value = variables.get(variableName);
        return value == null ? false : value;
    }

    public void assign(VariableExp exp, boolean value) {
        variables.put(exp.getName(), value);
    }
}
