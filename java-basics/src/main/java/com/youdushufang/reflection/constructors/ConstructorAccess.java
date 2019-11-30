package com.youdushufang.reflection.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static java.lang.System.out;

public class ConstructorAccess {

    public static void main(String[] args) {
        printConstructorModifiers(ConstructorAccess.class);
        printConstructorModifiers(SyntheticConstructorExample.class);
        printConstructorModifiers(String.class);
    }

    private static void printConstructorModifiers(Class<?> c) {
        Constructor[] allConstructors = c.getDeclaredConstructors();
        for (Constructor constructor : allConstructors) {
            out.format("%s%n", constructor.toGenericString());
            int modifiers = constructor.getModifiers();
            out.format("  [ public=%-5b package private=%-5b protected=%-5b private=%-5b ]%n",
                    Modifier.isPublic(modifiers),
                    !(Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)),
                    Modifier.isProtected(modifiers),
                    Modifier.isPrivate(modifiers));
            out.format("  [ synthetic=%-5b var_args=%-5b ]%n",
                    constructor.isSynthetic(), constructor.isVarArgs());
        }
    }
}
