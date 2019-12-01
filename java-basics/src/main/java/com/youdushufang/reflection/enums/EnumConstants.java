package com.youdushufang.reflection.enums;

import java.util.Arrays;

import static java.lang.System.out;

enum Eon { HADEAN, ARCHAEAN, PROTEROZOIC, PHANEROZOIC }

public class EnumConstants {

    public static void main(String[] args) {
        Class<Eon> c = Eon.class;
        out.format("Enum name:  %s%nEnum constants:  %s%n",
                c.getName(), Arrays.asList(c.getEnumConstants()));
    }
}
