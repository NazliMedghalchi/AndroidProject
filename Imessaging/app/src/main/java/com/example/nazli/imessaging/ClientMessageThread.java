package com.example.nazli.imessaging;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.textView;

// created to check read and write
public class ClientMessageThread extends Thread {
    InputStream in;
    OutputStream out;
    BufferedReader reader;
    PrintWriter writer;
    JSONObject jsonObject;
    Socket socket;

    public ClientMessageThread(Socket socket, JSONObject jsonObject){
        // listen on connected socket port
        try {
            this.out = socket.getOutputStream();
            this.in = socket.getInputStream();
            this.jsonObject = jsonObject;
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }

        readFromSocket();
        writeOnSocket();
    }
    public void readFromSocket(){
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            System.out.println(writer);
        }catch (IOException e) {
            e.printStackTrace();
        }
        writer.println(jsonObject.toString() + "\n");
        fromServer = "message Sent";
//            in.close();

//        }
//        System.out.println(writer.toString() + "\n");
    }

    public void writeOnSocket() {
        try {
            in = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            System.out.println(reader);
            textView.setText(reader.readLine() + reader.readLine().toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
