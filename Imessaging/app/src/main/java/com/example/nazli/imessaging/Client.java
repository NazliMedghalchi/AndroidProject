package com.example.nazli.imessaging;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.from_server;
import static com.example.nazli.imessaging.ChatActivity.itemText;
import static com.example.nazli.imessaging.ChatActivity.jsonArray;

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
    String response = "PreExecute";
    String txtResponse;
    ChatActivity chatActivity = new ChatActivity();
//    JSONParser jParser = new JSONParser();

    InputStream in;
    OutputStream out;
    BufferedReader reader;
    public Socket socket = null;

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
            socket = new Socket(destAddress, Integer.parseInt(Integer.toString(destPort)));
            fromServer += "connected";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
            fromServer += response;

        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        }
        publishProgress();
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
        fromServer += "Connected to Server on port: " + Integer.toString(destPort);
            if (itemText != null){
            try {
                ClientMessageThread();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onProgressUpdate();
    }
    // Show that server is running
    @Override
    protected void onPostExecute(Socket socket) {
        super.onPostExecute(socket);
        if (socket.isClosed()) {
            fromServer = "Server is ON";
        }
        else
            fromServer = "Server is Down";
    }

    // created to check read and write
    public void ClientMessageThread() throws IOException {
        // listen on connected socket port
        String jsonString;

         while (socket.isConnected()){
             out = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
             writer.write(jsonArray.toString());

             in = socket.getInputStream();
             reader = new BufferedReader(new InputStreamReader(in));
             fromServer = "message Sent";
//             textView.setText(reader.toString());
             out.flush();
         }
            in.close();

    }


}

