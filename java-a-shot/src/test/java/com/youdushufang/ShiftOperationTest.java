package com.youdushufang;

import org.junit.jupiter.api.Test;

import static com.youdushufang.utils.BinaryUtils.toBinaryString;

class ShiftOperationTest {

    @Test
    void testShiftOperations() {

        // 输出一个数的补码
        System.out.println("        1 = " + toBinaryString(1));
        System.out.println("       -1 = " + toBinaryString(-1));
        System.out.println("       35 = " + toBinaryString(35));
        System.out.println("      -35 = " + toBinaryString(-35));

        System.out.println();
        System.out.println("*****************");
        System.out.println();

        // 用 -99, 99 来演示三个位移操作符 <<、>>、>>>
        System.out.println("       99 = " + toBinaryString(99));
        System.out.println("  99 << 1 = " + (99 << 1));
        System.out.println("  99 << 1 = " + toBinaryString(99 << 1));
        System.out.println("  99 >> 1 = " + (99 >> 1));
        System.out.println("  99 >> 1 = " + toBinaryString(99 >> 1));
        System.out.println(" 99 >>> 1 = " + (99 >>> 1));
        System.out.println(" 99 >>> 1 = " + toBinaryString(99 >>> 1));

        System.out.println();

        System.out.println("      -99 = " + toBinaryString(-99));
        System.out.println(" -99 << 1 = " + ((-99) << 1));
        System.out.println(" -99 << 1 = " + toBinaryString((-99) << 1));
        System.out.println(" -99 >> 1 = " + ((-99) >> 1));
        System.out.println(" -99 >> 1 = " + toBinaryString((-99) >> 1));
        System.out.println("-99 >>> 1 = " + ((-99) >>> 1));
        System.out.println("-99 >>> 1 = " + toBinaryString((-99) >>> 1));

        System.out.println();
        System.out.println("*****************");
        System.out.println();
        int a = Integer.MIN_VALUE;
        System.out.println("     Integer.MIN_VALUE = " + a);
        System.out.println("     Integer.MIN_VALUE = " + toBinaryString(a));
        // 溢出了
        System.out.println("Integer.MIN_VALUE << 1 = " + (a << 1));
        System.out.println("Integer.MIN_VALUE << 1 = " + toBinaryString(a << 1));
        System.out.println();

        a = Integer.MAX_VALUE;
        System.out.println("     Integer.MAX_VALUE = " + a);
        System.out.println("     Integer.MAX_VALUE = " + toBinaryString(a));
        // 溢出了
        System.out.println("Integer.MAX_VALUE << 1 = " + (a << 1));
        System.out.println("Integer.MAX_VALUE << 1 = " + toBinaryString(a << 1));
        System.out.println();
    }
}
