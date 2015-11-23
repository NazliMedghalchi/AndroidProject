package Client;
import com.example.nazli.imessaging.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by nazlimedghalchi on 2015-11-10.
     * Ref: https://www.youtube.com/watch?v=d-eD6EDa3io
 */

/*
* This is webApp client/server architecture
* Server side is not based on this client
* */
public class Client {
    static String ip = "";
    static int port = 0;
    String fromServer = "";
    public Client(String ip, int port, String receiveServer) {
        this.ip = ip;
        this.port = port;
        this.fromServer = receiveServer;
    }

    public static void main(String[] args) throws Exception {
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        Socket cSocket;
        String status = "DOWN";
        String socketAddress = "host-android"; //"10.0.2.2";
        int port = 3306;
        final Scanner scanner = null;
        final String[] message = new String[1];
        try {
            cSocket = new Socket(socketAddress, port);
            status = changeSatuts(cSocket);
            // for receiving message
            final Scanner in = new Scanner(cSocket.getInputStream());
            // send out
            final PrintStream out = new PrintStream(cSocket.getOutputStream());
            Thread send = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        message[i] = scanner.next();
                        out.println(message[i]);
                        i++;
                        out.flush();
                    }
                }
            });
            send.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private static String changeSatuts(Socket sc) {
        if (sc != null)
            return "ON";
        return "DOWN";
    }
}
