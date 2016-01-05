package com.example.nazli.imessaging;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonWriter;

import org.json.JSONArray;

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
import static com.example.nazli.imessaging.ChatActivity.itemText;
import static com.example.nazli.imessaging.ChatActivity.textView;

/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<JSONArray, String , Socket> {

    String destAddress;
    Integer destPort;
    String response = "Before BG task";
    String txtResponse;

    BufferedReader reader;
    PrintWriter writer;
    InputStream in;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    public Socket socket;

//    http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    public InterfaceInOut interfaceInOut = null;

    Client(String address, int portNum, String txtRes) {
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }

    // Connect to socket in doInBackground
    @Override
    protected Socket doInBackground(JSONArray... params) {
        boolean c;
        try {
            socket = new Socket(destAddress, destPort);
            publishProgress();
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
        fromServer += String.format("Connected to Server on port: %s", destPort);
        super.onProgressUpdate();
    }
    // Show that server is running
    @Override
    protected void onPostExecute(Socket socket) {
//        ClientMessageThread clientMsg = null;
//        try {
//            clientMsg = new ClientMessageThread(socket, jsonArray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        clientMsg.run();
        fromServer += response + "Server is ON";
        interfaceInOut.resultAsync(fromServer);

        try {
//           Write on socket
            writeOnSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Read from socket
        readFromSocket();
        super.onPostExecute(socket);
    }

    public void writeOnSocket() throws IOException {
            try {
                if (itemText != null){
                    writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    System.out.println(writer);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
//        writer.println(jsonObject.toString() + "\n");
        fromServer = "message Sent";
//        System.out.println(writer + "\n");
    }

    public void readFromSocket() {
        try {
            if (reader != null) {
                in = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                textView.append(reader.readLine());
            }
            else {
                textView.append("Nothing to Read!!");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


