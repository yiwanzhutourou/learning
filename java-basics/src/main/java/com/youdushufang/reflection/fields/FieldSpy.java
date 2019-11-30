package com.youdushufang.reflection.fields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.lang.System.out;

enum Spy { BLACK , WHITE }

public class FieldSpy {

    public volatile int share;
    private static final int id = 0;

    private class InnerClass {
        private final int innerId = 0;
    }

    public static void main(String[] args) {
        // compiler generated synthetic field this$0
        searchFields(InnerClass.class, new String[]{ "final" });
        searchFields(Spy.class, new String[] { "public" });
        // compiler generated synthetic field $VALUES
        searchFields(Spy.class, new String[] { "private", "static", "final" });
        searchFields(FieldSpy.class, new String[] { "public", "volatile" });
    }

    private static void searchFields(Class<?> c, String[] searchFor) {
        int searchMods = 0x0;
        for (String modifier : searchFor) {
            searchMods |= modifierFromString(modifier);
        }
        Field[] fields = c.getDeclaredFields();
        out.format("Fields in Class '%s' containing modifiers:  %s%n",
                c.getName(), Modifier.toString(searchMods));
        for (Field f : fields) {
            int foundMods = f.getModifiers();
            // Require all of the requested modifiers to be present
            if ((foundMods & searchMods) == searchMods) {
                out.format("%-8s [ synthetic=%-5b enum_constant=%-5b ]%n",
                        f.getName(), f.isSynthetic(),
                        f.isEnumConstant());
            }
        }
    }

    private static int modifierFromString(String s) {
        int m = 0x0;
        if ("public".equals(s))           m |= Modifier.PUBLIC;
        else if ("protected".equals(s))   m |= Modifier.PROTECTED;
        else if ("private".equals(s))     m |= Modifier.PRIVATE;
        else if ("static".equals(s))      m |= Modifier.STATIC;
        else if ("final".equals(s))       m |= Modifier.FINAL;
        else if ("transient".equals(s))   m |= Modifier.TRANSIENT;
        else if ("volatile".equals(s))    m |= Modifier.VOLATILE;
        return m;
    }
}
