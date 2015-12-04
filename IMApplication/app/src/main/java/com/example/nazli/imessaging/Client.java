package com.example.nazli.imessaging;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.from_server;

/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<ChatArrayAdapter, String , Socket> {


    //    String SOCKET_CONNECT_TAG;
    String destAddress;
    int destPort;
    String response = "";
    String txtResponse;
    public Socket socket;

    JSONParser jObj = new JSONParser();

    Client(String address, int portNum, String txtRes) {
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }

    //
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        from_server.setText(response);
    }
//    ChatMessage chatMessage;

    @Override
    protected Socket doInBackground(ChatArrayAdapter... params) {

        try {
            fromServer = "Check Socket";
//            socket = new Socket(String.valueOf(
//                    new InetSocketAddress(destAddress, destPort)), 30);
            socket = new Socket(destAddress, destPort);
            if (socket.isConnected()) {
                fromServer += "connected";
            }

            // run sending message

        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
            fromServer += response;

        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        }
        return socket;
    }

    // I'm looking for this
//    protected void onProgressUpdate() throws InterruptedException, IOException {
//        fromServer += "Connected to Server on port: " + destPort;
//        super.onProgressUpdate();
//    }

    @Override
    protected void onPostExecute(Socket socket) {
        super.onPostExecute(socket);
        fromServer += "server is ON";
    }

    // created to check read and write


//            @Override
//            protected JSONObject doInBackground (JSONObject){
//                InputStreamReader inputStreamReader;
//                InputStream inputStream;
//                BufferedReader bufferedReader;
//                try {
//                    inputStream = socket.getInputStream();
//                    OutputStream outputStream = socket.getOutputStream();
//                    BufferedReader bufferR = new BufferedReader(new InputStreamReader(inputStream));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return jsonObj;
//            }

    public void ClientMessageThread() throws IOException {
            ChatActivity chatActivity = new ChatActivity();
            final JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("message", chatActivity.message.toString());
                jsonObj.put("userid", chatActivity.search.toString());
                String jsonString = jsonObj.toString();
                BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bWriter.write(jsonString);
                fromServer = "message Sent";

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

