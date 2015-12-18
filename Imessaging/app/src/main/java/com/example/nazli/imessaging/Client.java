package com.example.nazli.imessaging;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.from_server;
import static com.example.nazli.imessaging.ChatActivity.jsonObject;

/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<JSONObject, String , Socket> {

    String destAddress;
    Integer destPort;
    String response;
    String txtResponse;

    BufferedReader reader;
    PrintWriter writer;
    InputStream in;

//    ChatActivity chatActivity = new ChatActivity();
//    JSONParser jParser = new JSONParser();


    public Socket socket;

    Client(String address, int portNum, String txtRes) {
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }

    // Connect to socket in doinBackground
    @Override
    protected Socket doInBackground(JSONObject... params) {
        try {
//            fromServer = "Check Socket";
            socket = new Socket(destAddress, destPort);
            publishProgress();
            readFromSocket();
            writeOnSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        }
        return socket;
    }

    // Show before running asyncTask
    @Override
    protected void onPreExecute() {
        from_server.setText(response);
        super.onPreExecute();
    }
    // show the connection
    protected void onProgressUpdate(String...params) {
        super.onProgressUpdate();
        fromServer += String.format("Connected to Server on port: %s", destPort);
    }
    // Show that server is running
    @Override
    protected void onPostExecute(Socket socket) {
        fromServer += response + "Server is ON";
//        ClientMessageThread clientMsg = new ClientMessageThread(socket, jsonObject);
//        clientMsg.run();

        super.onPostExecute(socket);
//        fromServer += "Server is ON";
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
            System.out.println(reader.readLine());
//            textView.setText(reader.readLine() + reader.readLine().toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}





