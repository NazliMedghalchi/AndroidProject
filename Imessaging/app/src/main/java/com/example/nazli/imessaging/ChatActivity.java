package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import other.Message;
import other.SocketConfig;
import other.Utils;

//import Client.ClientJsonMessage;

//import com.codebutler.android_websockets.*;
//import com.google.gson.JsonParser;
//import com.koushikdutta.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

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
    public TextView from_server;

    EditText title;
    EditText search; // search contact here
    Button searchBTN;
    Button sendBTN; // on click on sendBTN the message will be sent to recipient (user)
    EditText message; // sent from user
    TextView itemText;
    Boolean side = false;

    ListView threadsList;
    ChatArrayAdapter chatArrayAdapter;
    Utils utils;

    Socket socket;
    Client client;

    final String ip = "10.0.2.15"; //turn it back to 10.0.2.15
    final int port = 6000; //back to 5554 to connect to emulator server app


    public void newChat(){
        message.setText("");
        search.setText("");
    }

    private void sendMessageToServer() {
        if (socket != null || !socket.isClosed()) {
            sendChatMessage();
        }
    }

    public boolean sendChatMessage(){
        // TODO: 2015-11-11 chat message to send:
        // https://trinitytuts.com/simple-chat-application-using-listview-in-android/
//        ChatMessage object = new ChatMessage(side, message.getText().toString());
        chatArrayAdapter.addAll();
        message.setText("");
        side = !side;
        return true;
    }


    public String  sendChatMessageToServer(String msg){
        return msg;
    }
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

        //DON'T move client call - it needs to be after instantiation


        TextView fromS = (TextView) findViewById(R.id.fromS);
        fromS.setText(fromServer);

        newChat();
    } //end of onCreate

    @Override
    protected void onStart(){
        super.onStart();
        try {
            client = new Client(ip, port, message.toString());
            client.execute();
        }catch (Exception e){
            e.printStackTrace();

            sendBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    sendChatMessageToServer(utils.getSendMessageJSON(message.getText()
                            .toString()));
                    Toast.makeText(getApplicationContext(), "Messaeg Sent", Toast.LENGTH_LONG).show();
                }
            });
            searchBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.list_of_contacts);
                    Intent i = new Intent(getApplicationContext(), ContactsList.class);
                    i.putExtra("search", search.toString());
                }
            }); // first clickListener
            chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),
                    R.layout.chat_item_right);


            sendBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // send message on inputstream to socket
                    sendMessageToServer();
                    // clear text field after sending
                    message.setText("");
                }
            });
        }
    }

 // TODO: 2015-11-19 destroy server socket
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

