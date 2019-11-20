package com.youdushufang.dp.facade.compiler;

import java.io.IOException;
import java.io.InputStream;

/**
 * 角色：门面
 *
 * 提供编译子系统的一些默认功能，方便使用
 * 如果调用方需要使用编译子系统的定制化功能，也可以直接访问所有子系统对外暴露的公共类、方法
 */
public interface Compiler {

    void compile(InputStream input, ByteCodeStream output) throws IOException;
}
