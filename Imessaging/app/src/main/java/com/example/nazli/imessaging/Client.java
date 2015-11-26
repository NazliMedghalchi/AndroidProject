package com.example.nazli.imessaging;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.midi.MidiInputPort;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.nazli.imessaging.ChatActivity.*;


/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<Void, Void, Void>{

    String destAddress;
    int destPort;
    String response = "";
    String txtResponse;

    Client(String address, int portNum, String txtRes){
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }
//
//    @Override
//    protected void onPreExecute(){
//        MainActivity.receiveServer = response;
//        super.onPreExecute();
//    }
    ChatMessage chatMessage;
    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket;

        try {
            socket = new Socket(destAddress, destPort);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
//            byte[] buffer = new byte[1024];
//            int byteStreamR;
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ChatActivity.fromServer = "Connected to Server on port: " + destPort;
//            while ((byteStreamR = inputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, byteStreamR);
//                response += byteArrayOutputStream.toString("UTF-8");
//                ChatActivity.fromServer= response;
//            }
//            byteArrayOutputStream.close(); // testing
        }catch (UnknownHostException e){
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
            ChatActivity.fromServer= response;

        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        final ChatActivity chatActivity = new ChatActivity();
        response += "server is ON";
        chatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fromServer = response;
            }
        });

    }
}
