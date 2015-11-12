package com.example.nazli.imessaging;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;

/**
 * Created by nazlimedghalchi on 2015-11-10.
 */
public class HTTPgetRequest extends AsyncTask<Void, Void, String> {

    private Socket clientSocket;
    private PrintWriter prinWriter;
    private String textMessage;
    static final String ADDRESS = "127.0.0.1";
    static final Integer PORT = 8888;

    private final String TAG = "HttpGetRequest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    HttpURLConnection httpURLConnection;


    @Override
    protected String doInBackground(Void... params) {
        try {
            clientSocket = new Socket(ADDRESS, PORT); //Connect to server
            prinWriter = new PrintWriter(clientSocket.getOutputStream());
            prinWriter.write(textMessage);


            prinWriter.flush();
            prinWriter.close();
            clientSocket.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
