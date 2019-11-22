package com.youdushufang.network;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EchoServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    @SuppressWarnings("all")
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new EchoWorker(clientSocket));
            }
        } catch (IOException e) {
            log.error("error: " + e);
            System.exit(1);
        }
    }

    private static class EchoWorker implements Runnable {

        private Socket clientSocket;

        EchoWorker(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (PrintWriter out  = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    log.info(inputLine);
                    out.println(inputLine);
                }
            } catch (IOException e) {
                log.error("error: " + e);
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
