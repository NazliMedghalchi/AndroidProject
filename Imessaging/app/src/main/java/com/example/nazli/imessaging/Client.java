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
    Integer destPort;
    String response;
    String txtResponse;
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
//            publishProgress();
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
        fromServer += String.format("Connected to Server on port: %s", Integer.toString(destPort));
    }
    // Show that server is running
    @Override
    protected void onPostExecute(Socket socket) {
        fromServer += response + "Server is ON";
        super.onPostExecute(socket);
//        fromServer += "Server is ON";
    }
}





