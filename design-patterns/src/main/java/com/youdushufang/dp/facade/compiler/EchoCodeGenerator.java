package com.youdushufang.dp.facade.compiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * CodeGenerator 的具体实现
 */
public class EchoCodeGenerator extends CodeGenerator {

    public EchoCodeGenerator(ByteCodeStream output) {
        super(output);
    }

    @Override
    public void visit(ExpressionNode expressionNode) {
        // do nothing
    }

    public void visit(VariableNode variableNode) throws IOException {
        output.write((variableNode.getVariableName() + "\n").getBytes(StandardCharsets.UTF_8));
    }
}
