package com.example.nazli.imessaging;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by nazlimedghalchi on 2015-11-10.
 * Ref: https://www.youtube.com/watch?v=d-eD6EDa3io
 */
public class Client {
    public static void main(String[] args) {
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        Socket socketClient;

//        Socket serverSocket;
        final BufferedReader in;
        final PrintWriter out;
        // Read from keyboard
        final Scanner scanner = new Scanner(System.in);
        try {
            socketClient = new Socket("127.0.0.1", 8888);

            // send out
            out = new PrintWriter(socketClient.getOutputStream());
            // for receiving
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread send = new Thread(new Runnable() {
            PrintStream out;
            String text = "Hello server, it's client";

            @Override
            public void run() {
                while (true) {
                    text = scanner.next();
                    out.println(text);
                    out.flush();

                }
            }
        });
        send.start();
        Thread receive = new Thread(new Runnable() {
            String text;
            BufferedReader in;

            @Override
            public void run() {
                while (true) {
                    try {
                        text = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Client: " + text);
                }
            }
        });
        receive.start();
    }
}
