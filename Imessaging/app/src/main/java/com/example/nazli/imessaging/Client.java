package com.example.nazli.imessaging;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.chatMessage;
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
    int destPort;
    String response = "";
    String txtResponse;
    public Socket socket;
    ChatActivity chatActivity = new ChatActivity();
    JSONObject jObj = new JSONObject();
    JSONParser parser = new JSONParser();

    Client(String address, int portNum, String txtRes) {
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }

    @Override
    protected Socket doInBackground(JSONObject... params) {
    Socket socket = null;

        try {
            fromServer = "Check Socket";
//            socket = new Socket(String.valueOf(
//                    new InetSocketAddress(destAddress, destPort)), 30);
            socket = new Socket(destAddress, destPort);
//            if (socket.isBound()) {
                fromServer += "connected";
//            }

            // run sending message

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
                textView.setText(chatMessage.toString());
                ClientMessageThread(jObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onProgressUpdate();
    }
    @Override
    protected void onPostExecute(Socket socket) {
        fromServer = "Server is ON";
        super.onPostExecute(socket);
    }

    // created to check read and write

    public void ClientMessageThread(JSONObject jObj) throws IOException {
        JSONParser jParser = new JSONParser();
        JSONObject jsonObj;
        jsonObj = jObj;
        ChatActivity chatActivity = new ChatActivity();
        String jsonString = jsonObj.toString();
        OutputStream out = socket.getOutputStream();
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(out));
        jParser.getJSONFromUrl(out.toString());
        write.write(jsonString);
        fromServer = "message Sent";
        textView.setText(jsonString);
//                    bWriter.flush();

    }


}

