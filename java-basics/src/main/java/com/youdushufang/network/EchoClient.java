package com.youdushufang.network;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class EchoClient {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(
                     new InputStreamReader(System.in))) {
            String line;
            while ((line = stdIn.readLine()) != null) {
                out.println(line);
                log.info("echo: " + in.readLine());
            }
        } catch (IOException e) {
            log.error("error: " + e);
            System.exit(1);
        }
    }
}
