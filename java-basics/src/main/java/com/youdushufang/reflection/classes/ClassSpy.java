package com.youdushufang.reflection.classes;

import com.youdushufang.reflection.SuperClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import static java.lang.System.out;

public class ClassSpy extends SuperClass {

    private String name;

    public ClassSpy() {
        this("ClassSpy");
    }

    private ClassSpy(String name) {
        this.name = name;
    }

    private static class MyInnerClass { }

    public static void main(String[] args) {
        Class<?> c = ClassSpy.class;
        out.format("Class:%n  %s%n%n", c.getCanonicalName());

        Package p = c.getPackage();
        out.format("Package:%n  %s%n%n",
                (p != null ? p.getName() : "-- No Package --"));
        printMembers(c.getConstructors(), "Constuctors");
        printMembers(c.getFields(), "Fields");
        printMembers(c.getMethods(), "Methods");
        printClasses(c.getClasses(), "Classes");

        printMembers(c.getDeclaredConstructors(), "Declared Constuctors");
        printMembers(c.getDeclaredFields(), "Declared Fields");
        printMembers(c.getDeclaredMethods(), "Declared Methods");
        printClasses(c.getDeclaredClasses(), "Declared Classes");
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format("  %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format("  %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format("  %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format("  -- No %s --%n", s);
        out.format("%n");
    }

    private static void printClasses(Class<?>[] clss, String s) {
        out.format("%s:%n", s);
        for (Class<?> cls : clss)
            out.format("  %s%n", cls.getCanonicalName());
        if (clss.length == 0)
            out.format("  -- No member interfaces, classes, or enums --%n");
        out.format("%n");
    }
}
