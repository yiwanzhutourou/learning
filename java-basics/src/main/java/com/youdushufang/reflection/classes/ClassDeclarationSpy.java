package com.youdushufang.reflection.classes;

import com.youdushufang.reflection.MyAnnotation;
import com.youdushufang.reflection.SuperClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Data
@EqualsAndHashCode(callSuper = false)
@MyAnnotation(value = "ClassDeclarationSpy", id = 1)
public final class ClassDeclarationSpy<K, V> extends SuperClass
        implements Serializable, Cloneable {

    private K key;

    private V value;

    public static void main(String[] args) {
        Class<?> c = ClassDeclarationSpy.class;
        out.format("Class:%n  %s%n%n", c.getCanonicalName());
        // 打印类修饰关键字
        out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));

        // 打印类上定义的泛型
        out.format("Type Parameters:%n");
        TypeVariable[] tv = c.getTypeParameters();
        if (tv.length != 0) {
            out.format("  ");
            for (TypeVariable t : tv)
                out.format("%s ", t.getName());
            out.format("%n%n");
        } else {
            out.format("  -- No Type Parameters --%n%n");
        }

        // 打印实现的接口
        out.format("Implemented Interfaces:%n");
        Type[] intfs = c.getGenericInterfaces();
        if (intfs.length != 0) {
            for (Type intf : intfs)
                out.format("  %s%n", intf.toString());
            out.format("%n");
        } else {
            out.format("  -- No Implemented Interfaces --%n%n");
        }

        // 打印继承链
        out.format("Inheritance Path:%n");
        List<Class> l = new ArrayList<>();
        searchAncestor(c, l);
        if (l.size() != 0) {
            for (Class<?> cl : l)
                out.format("  %s%n", cl.getCanonicalName());
            out.format("%n");
        } else {
            out.format("  -- No Super Classes --%n%n");
        }

        // 打印类上定义的注解，之所以只打印出一个注解，是因为其他注解的 Retention 都不是 RUNTIME
        out.format("Annotations:%n");
        Annotation[] ann = c.getAnnotations();
        if (ann.length != 0) {
            for (Annotation a : ann)
                out.format("  %s%n", a.toString());
            out.format("%n");
        } else {
            out.format("  -- No Annotations --%n%n");
        }
    }

    private static void searchAncestor(Class<?> c, List<Class> l) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            searchAncestor(ancestor, l);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
