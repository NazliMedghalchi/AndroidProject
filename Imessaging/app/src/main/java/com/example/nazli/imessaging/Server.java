package com.example.nazli.imessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

/**
 * Created by nazlimedghalchi on 2015-11-10.
 * Ref: https://www.youtube.com/watch?v=d-eD6EDa3io
 */
public class Server {
    public static void main (String[] args) {
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        Socket socketClient;
        ServerSocket serverSocket;

//        Socket serverSocket;
        final BufferedReader in;
        final PrintWriter out;
        // Read from keyboard
        final Scanner scanner = new Scanner(System.in);
        try {
            // create server address by means of port number
            serverSocket = new ServerSocket(5000);
            // listening for connection
            socketClient = serverSocket.accept();

            // send out
            out = new PrintWriter(socketClient.getOutputStream());
            // for receiving
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Thread send = new Thread(new Runnable() {
            PrintStream out;
            String text;
            @Override
            public void run() {
                while(true){
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
                while(true){
                    try {
                        text = in.readLine();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Server: " + text);
                }
            }
        });
        receive.start();

    }

}
