package com.example.nazli.imessaging;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.from_server;
import static com.example.nazli.imessaging.ChatActivity.jsonObject;
import static com.example.nazli.imessaging.ChatActivity.textView;

/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<JSONObject, String , Socket> {

    String destAddress;
    int destPort;
    String response = "PreExecute";
    String txtResponse;
    ChatActivity chatActivity = new ChatActivity();
//    JSONParser jParser = new JSONParser();

    InputStream in;
    BufferedReader reader;
    public Socket socket = null;

    Client(String address, int portNum, String txtRes) {
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }

    @Override
    protected Socket doInBackground(JSONObject... params) {

        try {
//            fromServer = "Check Socket";
            socket = new Socket(destAddress, destPort);
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

    // I'm looking for this

    @Override
    protected void onPreExecute() {
        from_server.setText(response);
        super.onPreExecute();
    }
    protected void onProgressUpdate(String...params) {
        fromServer += "Connected to Server on port: " + destPort;
        if (chatActivity.itemText != null){
            try {
                ClientMessageThread();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onProgressUpdate();
    }
    @Override
    protected void onPostExecute(Socket socket) {
        super.onPostExecute(socket);
        fromServer += "Server is ON";
    }

    // created to check read and write

    public void ClientMessageThread() throws IOException {

        OutputStream out = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(jsonObject.toString());

        String jsonString;
        in = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(in));
        fromServer = "message Sent";
        jsonString = jsonObject.toString();
        textView.setText(jsonString);
        in.close();
        out.flush();
    }


}

