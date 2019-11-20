package com.youdushufang.dp.facade.compiler;

/**
 * 角色：编译子系统的一员
 *
 * 语法分析器，根据标记（token）流生成语法树
 */
public abstract class Parser {

    abstract void parse(Scanner scanner, ProgramNodeBuilder programNodeBuilder);
}
