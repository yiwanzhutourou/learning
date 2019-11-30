package com.youdushufang.reflection.methods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static java.lang.System.out;

public class MethodParameterSpy {

    private static final String  fmt = "%24s: %s%n";

    private static void printClassConstructors(Class c) {
        Constructor[] allConstructors = c.getConstructors();
        out.format(fmt, "Number of constructors", allConstructors.length);
        for (Constructor currentConstructor : allConstructors) {
            printConstructor(currentConstructor);
        }
        Constructor[] allDeclConst = c.getDeclaredConstructors();
        out.format(fmt, "Number of declared constructors",
                allDeclConst.length);
        for (Constructor currentDeclConst : allDeclConst) {
            printConstructor(currentDeclConst);
        }
    }

    private static void printClassMethods(Class c) {
        Method[] allMethods = c.getDeclaredMethods();
        out.format(fmt, "Number of methods", allMethods.length);
        for (Method m : allMethods) {
            printMethod(m);
        }
    }

    private static void printConstructor(Constructor c) {
        out.format("%s%n", c.toGenericString());
        Parameter[] params = c.getParameters();
        out.format(fmt, "Number of parameters", params.length);
        for (Parameter param : params) {
            printParameter(param);
        }
    }

    private static void printMethod(Method m) {
        out.format("%s%n", m.toGenericString());
        out.format(fmt, "Return type", m.getReturnType());
        out.format(fmt, "Generic return type", m.getGenericReturnType());

        Parameter[] params = m.getParameters();
        for (Parameter param : params) {
            printParameter(param);
        }
    }

    private static void printParameter(Parameter p) {
        out.format(fmt, "Parameter class", p.getType());
        out.format(fmt, "Parameter name", p.getName());
        out.format(fmt, "Modifiers", p.getModifiers());
        out.format(fmt, "Is implicit?", p.isImplicit());
        out.format(fmt, "Is name present?", p.isNamePresent());
        out.format(fmt, "Is synthetic?", p.isSynthetic());
    }

    public static void main(String[] args) {
        Class<?> c = ExampleMethods.class;
        printClassConstructors(c);
        printClassMethods(c);

        printSeparator();

        Class<?> innerClass = ExampleMethods.InnerClass.class;
        printClassConstructors(innerClass);

        printSeparator();

        Class<?> enumClass = ExampleMethods.Colors.class;
        printClassConstructors(enumClass);
        printClassMethods(enumClass);
    }

    private static void printSeparator() {
        out.println();
        out.println("**********");
        out.println();
    }
}
