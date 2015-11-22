package com.example.nazli.imessaging;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.Client;

/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatService is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */
public class ClientServer extends AsyncTask<Void, Void, Void>{

    String destAddress;
    int destPort;
    String response = "";
    TextView txtResponse;

    ClientServer(String address, int portNum, TextView txtRes){
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }


    @Override
    protected void onPreExecute(){

    }
    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(destAddress, destPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int byteStreamR;
            InputStream inputStream = socket.getInputStream();

            while ((byteStreamR = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, byteStreamR);
                response += byteArrayOutputStream.toString("UTF-8");
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        txtResponse.setText(response);
        super.onPostExecute(result);
    }
}
