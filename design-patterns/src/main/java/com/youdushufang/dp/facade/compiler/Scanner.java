package com.youdushufang.dp.facade.compiler;

import java.io.InputStream;

/**
 * 角色：编译子系统的一员
 *
 * 将字节流变成标记（token）流
 */
public abstract class Scanner {

    private InputStream input;

    public Scanner(InputStream input) {
        this.input = input;
    }

    /**
     * scan 函数一次返回一个标记，并移动到到下一个标记处
     *
     * @return 当前 token
     */
    abstract Token scan();
}
