package com.youdushufang.test.io;

import org.junit.jupiter.api.Test;

import java.io.*;

class DataStreamTest {

    @Test
    void testDataOutputStream() throws IOException {
        final String dataFile = "io_files/invoicedata";
        final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
        final int[] units = { 12, 8, 13, 29, 50 };
        final String[] descs = {
                "Java T-shirt",
                "Java Mug",
                "Duke Juggling Dolls",
                "Java Pin",
                "Java Key Chain"
        };

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            for (int i = 0; i < prices.length; i ++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }
        }
    }

    @Test
    void testDataInputStream() throws IOException {
        final String dataFile = "io_files/invoicedata";
        double price;
        int unit;
        String desc;

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(dataFile)))) {
            while (true) {
                price = in.readDouble();
                unit = in.readInt();
                desc = in.readUTF();
                System.out.format("You ordered %d" + " units of %s at $%.2f%n",
                        unit, desc, price);
            }
        } catch (EOFException e) {
            // end of file
        }
    }
}
