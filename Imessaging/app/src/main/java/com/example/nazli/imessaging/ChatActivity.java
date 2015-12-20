package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

import other.Utils;

//import Client.ClientJsonMessage;
//import com.codebutler.android_websockets.*;
//import com.google.gson.JsonParser;
//import com.koushikdutta.http.AsyncHttpClient;

/*
 * Created by nazlimedghalchi on 2015-11-03.
 *
 **/

   /*
            * permission is added
            * http://developer.android.com/training/contacts-provider/retrieve-details.html
            *
            * In order to simplify, just using string entered in editText view
            * Hopefully user enters correct number
            **/

// chat messages list adapter
// http://www.androidhive.info/2014/10/android-building-group-chat-app-using-sockets-part-2/
/*    The setOnkeyListener is for, if the number is in contacts list saved on phone, then make send btn clickable
    Otherwise it is not clickable
    The listener for sendBTN takes message and pass it to upStream*/

public class ChatActivity extends Activity {

    public static String fromServer = null;
    public static TextView from_server;
    public static TextView textView;
    public static ChatMessage chatMessage;
    public static JSONObject jsonObject;
    public static JSONArray jsonArray;
    public static EditText search;
    public static TextView itemText;


    EditText title;
    // search contact here
    Button searchBTN;
    // on click on sendBTN the message will be sent to recipient (user)
    Button sendBTN;
    // sent from user
    EditText message;
    Boolean side = false;

    Boolean clickedSent = false;

    ListView threadsList;
    public ChatArrayAdapter chatArrayAdapter;
    Utils utils;
    Socket socket;
    Client client;

    final String ip = "10.0.2.2"; //turn it back to 10.0.2.15 /
    final int port = 8080; //back to 5554 to connect to emulator server app - 6000
    TextView socketIP;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_chat);

        from_server = (TextView) findViewById(R.id.from_server);
        search = (EditText) findViewById(R.id.editText_search); // search contact here
        searchBTN = (Button) findViewById(R.id.search_btn);
        title = (EditText) findViewById(R.id.editText_conv_title);
        itemText = (TextView) findViewById(R.id.convFirstLine);
        threadsList = (ListView) findViewById(R.id.listView_chat);

        sendBTN = (Button) findViewById(R.id.btn_send); // on click on sendBTN the message will be sent to recipient (user)
        textView = (TextView) findViewById(R.id.textView_message);
        message = (EditText) findViewById(R.id.editText_msg); // sent from user

        side = false;

        //DON'T move client call - it needs to be after instantiation

        socketIP = (TextView) findViewById(R.id.socketIP);
        newChat();

        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
        textView.setText(message.toString());

        client = new Client(ip, port, from_server.toString());
        client.execute();
        socketIP.setText(ip + ": " + port);
        fromServer = "Connected to Server";
        from_server.setText(fromServer);
    } //end of onCreate

    @Override
    protected void onStart(){
        super.onStart();
        clickedSent = false;
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // send message on outputStream to socket
                clickedSent = true;
            }
        });

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactsList.class);
                i.putExtra("userID", search.toString());
            }
        }); // first clickListener

        while (clickedSent) {
            try {
                try {
                    jsonObject.put("message", itemText.toString());
                    jsonObject.put("userid", search.toString());
                    jsonArray.put(jsonObject);
                    sendMessageToServer();
                    textView.setText(jsonObject.getString("message" + "userid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendMessageToServer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear text field after sending
            message.setText(" ");
        }

        if (client.getStatus() == AsyncTask.Status.PENDING){
            fromServer += "Pending for server connection";
        }
    } // End of onStart

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try {
            fromServer = "Disconnected";
            newChat();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newChat(){
        message.setText("");
        search.setText("");
    }

    // check socket
    // Check out: https://trinitytuts.com/simple-chat-application-using-listview-in-android/

    private boolean sendMessageToServer() throws IOException, InterruptedException {
        ClientMessageThread clientMessageThread = new ClientMessageThread(socket, jsonArray);
        clientMessageThread.start();
        message.setText(" ");
        return true;
    }
}

