package com.youdushufang.dp.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 角色：Subject，被代理对象抽象接口
 */
public interface Graphic {

    void draw(Point where);

    void handleMouse(Event event);

    Point getExtent();

    void load(InputStream in) throws IOException;

    void save(OutputStream out) throws IOException;
}
