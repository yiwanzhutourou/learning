package com.youdushufang.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MulticastServer {

    public static void main(String[] args) {
        new MulticastServerThread().start();
    }

    private static class MulticastServerThread extends Thread {

        private static final List<String> quotes = Arrays.asList(
                "Life is wonderful. Without it we'd all be dead.",
                "Daddy, why doesn't this magnet pick up this floppy disk?",
                "Give me ambiguity or give me something else.",
                "I.R.S.: We've got what it takes to take what you've got!",
                "We are born naked, wet and hungry. Then things get worse."
        );

        MulticastServerThread() {
            this("MulticastServerThread");
        }

        MulticastServerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket(4446)) {
                for (String quote : quotes) {
                    byte[] buf = quote.getBytes(StandardCharsets.UTF_8);
                    InetAddress group = InetAddress.getByName("225.0.0.1");
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                    socket.send(packet);
                    System.out.println("Quote of the Moment: " + quote);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
