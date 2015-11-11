package com.example.nazlimedghalchi.myapplication.backend;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by nazlimedghalchi on 2015-11-04.
 */
public abstract class Server {

    public static void main (String[] args) {
        //    // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        ServerSocket serverSocket;
        // Read from keyboard
        Scanner scanner = new Scanner(System.in);




        // send out
        final BufferedWriter out;
        Socket serverSocket;

        // create server address by means of port number
        try {
            serverSocket = new ServerSocket(5000).accept();
            out = new PrintWriter(serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private Socket clientSocket = serverSocket.
    private InputStream inputStream = new InputStream() {
        @Override
        public int read() throws IOException {
            final BufferedReader in;

            return 0;
        }
    };

    private OutputStream outputStream = new OutputStream() {
        @Override
        public void write(int oneByte) throws IOException {

        }
    };
}
