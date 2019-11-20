package com.youdushufang.dp.facade.compiler;

import java.io.IOException;

/**
 * 语法树的一个节点
 *
 * 可能有 StatementNode、ExpressionNode 等子类
 */
public interface ProgramNode {

    // 获取在源代码中的位置
    SourcePosition getSourcePosition();

    // 增加子节点
    void add(ProgramNode childNode);

    // 删除子节点
    void remove(ProgramNode childNode);

    // 生成字节码
    void traverse(CodeGenerator codeGenerator) throws IOException;

    public static class SourcePosition {

        private int line;

        private int index;

        public int getLine() {
            return line;
        }

        public void setLine(int line) {
            this.line = line;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
