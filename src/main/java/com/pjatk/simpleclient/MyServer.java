package com.pjatk.simpleclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class MyServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("good bye");
                    break;
                }
                out.println(inputLine);
            }
        } catch (IOException e) {
            log(e.getMessage());
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            log(e.getMessage());
        }

    }

    public static void main(String[] args) {
        MyServer server = new MyServer();
        server.start(8888);
    }

    private static void log(String msg){
        System.out.println("[" + Thread.currentThread().getName() + "] - [" + msg + "]");
    }
}
