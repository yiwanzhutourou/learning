package com.youdushufang.dp.facade.compiler;

public class ExpressionNodeBuilder implements ProgramNodeBuilder {

    private final ExpressionNode root;

    public ExpressionNodeBuilder() {
        this.root = new ExpressionNode();
    }

    @Override
    public ProgramNode newVariable(String variableName) {
        VariableNode variableNode = new VariableNode(variableName);
        root.add(variableNode);
        return variableNode;
    }

    @Override
    public ProgramNode getRootNode() {
        return root;
    }
}
