package com.youdushufang.dp.facade.compiler;

import java.io.IOException;

public class VariableNode implements ProgramNode {

    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public SourcePosition getSourcePosition() {
        // 实现省略
        return new SourcePosition();
    }

    @Override
    public void add(ProgramNode childNode) {
        // not supported, do nothing
    }

    @Override
    public void remove(ProgramNode childNode) {
        // not supported, do nothing
    }

    @Override
    public void traverse(CodeGenerator codeGenerator) throws IOException {
        codeGenerator.visit(this);
    }

    public String getVariableName() {
        return this.variableName;
    }
}
