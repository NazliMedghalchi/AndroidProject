package com.example.nazli.imessaging;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by nazlimedghalchi on 2015-11-04.
 */
public abstract class ServerMain {

    public static void main (String[] args) {
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        Socket socket = new Socket();
        ServerSocket serverSocket;
//        Socket serverSocket;
        final BufferedReader in;
        final PrintWriter out;
        // Read from keyboard
        final Scanner scanner = new Scanner(System.in);
        try {
            // create server address by means of port number
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            // send out
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            PrintWriter out;
            String text;
            @Override
            public void run() {
                while(true){
                    text = scanner.next();
                    out.write(text);
                    out.flush();
                }
            }
        });

    }

//    private Socket clientSocket = serverSocket.
private InputStream inputStream = new InputStream() {
    @Override
    public int read() throws IOException {

        return 0;
    }
};
    private OutputStream outputStream = new OutputStream() {
        @Override
        public void write(int oneByte) throws IOException {

        }
    };

    public void connectToServer(){
        Socket socket = new Socket();
//        outputStream.write(MidiDeviceInfo.PortInfo.TYPE_OUTPUT);

    }



}
