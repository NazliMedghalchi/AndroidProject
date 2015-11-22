package Client;
import com.example.nazli.imessaging.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Server.Server;

/**
 * Created by nazlimedghalchi on 2015-11-10.
     * Ref: https://www.youtube.com/watch?v=d-eD6EDa3io
 */

/*
* This is webApp client/server architecture
* Server side is not based on this client
* */
public class Client {
    public static void main(String[] args) throws Exception{
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
        Socket socketClient;
        ChatService chatService = new ChatService();
        Server server = new Server(chatService);
        String socketAddress = server.getIpAddress(); //"10.0.2.2";
        int port = server.getPort();
        final Scanner scanner = null;
        try {
            try {
                socketClient = new Socket(socketAddress, port);
                // for receiving message
                Scanner in = new Scanner(socketClient.getInputStream());
                // send out
                PrintStream out = new PrintStream(socketClient.getOutputStream());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread send = new Thread(new Runnable() {
            PrintStream out;
            ArrayList<String> text = new ArrayList<String>();
//            text.add("Hello server, it's client");
            @Override
            public void run() {
                while (true) {
//                    text = scanner.next();
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
                    System.out.println("ClientServer: " + text);
                }
            }
        });
        receive.start();
    }
}
