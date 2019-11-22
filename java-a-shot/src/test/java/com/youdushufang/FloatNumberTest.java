package com.youdushufang;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.youdushufang.utils.BinaryUtils.toBinaryString;

class FloatNumberTest {

    @Test
    void testFloatBinary() {
        System.out.println("16.0f = " + toBinaryString(Float.floatToIntBits(16.0f)));
        System.out.println("0.35f = " + toBinaryString(Float.floatToIntBits(0.35f)));
        System.out.println("0.35f = " + toBinaryString(Float.floatToIntBits(16.35f)));
    }

    @Test
    void testFloatEquals() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;

        System.out.println("1.0f - 0.9f = " + a);
        System.out.println("0.9f - 0.8f = " + b);

        BigDecimal x = new BigDecimal("1.0");
        BigDecimal y = new BigDecimal("0.9");
        BigDecimal z = new BigDecimal("0.8");
        BigDecimal m = x.subtract(y);
        BigDecimal n = y.subtract(z);

        System.out.println("1.0f - 0.9f = " + m);
        System.out.println("0.9f - 0.8f = " + n);
        System.out.println(m.equals(n));
    }
}
