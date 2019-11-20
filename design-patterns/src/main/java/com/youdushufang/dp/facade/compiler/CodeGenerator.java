package com.youdushufang.dp.facade.compiler;

import java.io.IOException;

/**
 * 角色：编译子系统的一员
 *
 * 根据语法树生成字节码，采用了访问者模式（Visitor）
 */
public abstract class CodeGenerator {

    protected ByteCodeStream output;

    public CodeGenerator(ByteCodeStream output) {
        this.output = output;
    }

    abstract void visit(ExpressionNode expressionNode) throws IOException;

    abstract void visit(VariableNode variableNode) throws IOException;
}
