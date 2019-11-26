package com.youdushufang.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class QuoteServer {

    public static void main(String[] args) {
        new QuoteServerThread().start();
    }

    private static class QuoteServerThread extends Thread {

        private static final List<String> quotes = Arrays.asList(
                "Life is wonderful. Without it we'd all be dead.",
                "Daddy, why doesn't this magnet pick up this floppy disk?",
                "Give me ambiguity or give me something else.",
                "I.R.S.: We've got what it takes to take what you've got!",
                "We are born naked, wet and hungry. Then things get worse."
        );

        QuoteServerThread() {
            this("QuoteServerThread");
        }

        QuoteServerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket(4445)) {
                int index = 0;
                while (index < quotes.size()) {
                    byte[] buf = new byte[256];
                    DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                    socket.receive(datagramPacket);
                    buf = quotes.get(index).getBytes(StandardCharsets.UTF_8);
                    InetAddress address = datagramPacket.getAddress();
                    int port = datagramPacket.getPort();
                    datagramPacket = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(datagramPacket);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
