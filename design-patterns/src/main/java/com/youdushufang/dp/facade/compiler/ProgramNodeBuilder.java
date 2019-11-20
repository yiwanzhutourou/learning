package com.youdushufang.dp.facade.compiler;

/**
 * 语法树节点建造器，建造者模式（Builder）
 */
public interface ProgramNodeBuilder {

    ProgramNode newVariable(String variableName);

    ProgramNode getRootNode();
}
