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

    public static String fromServer;
    public static TextView from_server;
    public static TextView textView;
    public static ChatMessage chatMessage;
    public static JSONObject jsonObject;
    public static JSONArray jsonArray;

    EditText title;
    EditText search; // search contact here
    Button searchBTN;
    Button sendBTN; // on click on sendBTN the message will be sent to recipient (user)
    EditText message; // sent from user
    public static TextView itemText;
    Boolean side = false;

    ListView threadsList;
    public ChatArrayAdapter chatArrayAdapter;
    Utils utils;
    Socket socket;
    Client client;


    final String ip = "10.0.2.2"; //turn it back to 10.0.2.15 /
    final int port = 8080; //back to 5554 to connect to emulator server app - 6000
    TextView fromS;


    @Override
    public void onCreate(Bundle savaedInstance) {
        super.onCreate(savaedInstance);
        setContentView(R.layout.activity_chat);

        from_server = (TextView) findViewById(R.id.from_server);
        search = (EditText) findViewById(R.id.editText_search); // search contact here
        searchBTN = (Button) findViewById(R.id.search_btn);
        title = (EditText) findViewById(R.id.editText_conv_title);
        sendBTN = (Button) findViewById(R.id.btn_send); // on click on sendBTN the message will be sent to recipient (user)
        message = (EditText) findViewById(R.id.editText_msg); // sent from user
        itemText = (TextView) findViewById(R.id.convFirstLine);
        threadsList = (ListView) findViewById(R.id.listView_chat);
        side = false;

        textView = (TextView) findViewById(R.id.textView_message);
        //DON'T move client call - it needs to be after instantiation

        fromS = (TextView) findViewById(R.id.fromS);
        newChat();

        jsonArray = new JSONArray();
        jsonObject = new JSONObject();

//        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),
//                R.layout.chat_item_right);

//        textView.setText(message.toString());



    } //end of onCreate

    @Override
    protected void onStart(){
        super.onStart();
        client = new Client(ip, port, from_server.toString());
        client.execute();
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactsList.class);
                i.putExtra("userID", search.toString());
                startActivity(i);
            }
        }); // first clickListener

        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // send message on outputstream to socket
                while (itemText != null) {
                    try {
                        try {
                            if (itemText != null) {
                                jsonObject.put("message", itemText.toString());
                                jsonObject.put("userid", search.toString());
                                jsonArray.put(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sendMessageToServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // clear text field after sending
                message.setText(" ");
            }
        });

        if (client.getStatus() == AsyncTask.Status.PENDING){
            fromServer += "Pending for server connection";
        }

        from_server.setText(fromServer);
        fromS.setText(ip);
        try {
            if (sendBTN.isPressed()){
                client.ClientMessageThread();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

 // TODO: 2015-11-19 destroy server socket
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (!socket.isClosed()) {
            try {
                socket.close();
                fromServer = "Disconnected";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void newChat(){
        message.setText("");
        search.setText("");
    }

    // check socket
    private void sendMessageToServer() throws IOException, InterruptedException {
//        if (socket.isConnected()) {
            sendChatMessage();
//        }
    }

    //send message on output stream
    public boolean sendChatMessage() throws IOException, InterruptedException {
        // TODO: 2015-11-11 chat message to send:
        // https://trinitytuts.com/simple-chat-application-using-listview-in-android/
//        ChatMessage messageObj = new ChatMessage(true, message.getText().toString(), "10");
//        chatArrayAdapter.addAll();
        client.ClientMessageThread();
        message.setText(" ");
//        side = !side;
        return true;
    }
//    public String  sendChatMessageToServer(String msg){
//        return msg;
//    }

}

