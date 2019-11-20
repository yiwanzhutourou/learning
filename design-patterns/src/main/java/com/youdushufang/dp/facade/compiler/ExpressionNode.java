package com.youdushufang.dp.facade.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表达式节点，伪代码实现
 */
public class ExpressionNode implements ProgramNode {

    private List<ProgramNode> children;

    public ExpressionNode() {
        this.children = new ArrayList<>();
    }

    @Override
    public SourcePosition getSourcePosition() {
        // 实现省略
        return new SourcePosition();
    }

    @Override
    public void add(ProgramNode childNode) {
        Objects.requireNonNull(childNode);
        children.add(childNode);
    }

    @Override
    public void remove(ProgramNode childNode) {
        Objects.requireNonNull(childNode);
        children.remove(childNode);
    }

    @Override
    public void traverse(CodeGenerator codeGenerator) throws IOException {
        codeGenerator.visit(this);
        // 递归调用子节点
        for (ProgramNode childNode : children) {
            childNode.traverse(codeGenerator);
        }
    }
}
