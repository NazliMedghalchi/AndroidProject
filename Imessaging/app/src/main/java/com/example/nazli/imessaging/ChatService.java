package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebMessage;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import other.Message;
import other.SocketConfig;
import other.Utils;

import com.codebutler.android_websockets.*;
import com.google.gson.JsonParser;
import com.koushikdutta.http.AsyncHttpClient;

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
//        Client client  = new Client();

public class ChatService extends Activity {

    EditText title;
    EditText search; // search contact here
    Button searchBTN;
    Button sendBTN; // on click on sendBTN the message will be sent to recipient (user)
    EditText message; // sent from user
    TextView itemText;
    Boolean side = false;

    public TextView from_server;
//    ContentValues values = new ContentValues();
//    String textMSG = message.toString();
    ListView threadsList;
    ChatArrayAdapter chatArrayAdapter;

    private List<Message> listMessages;
    private Utils utils;
    private String name = null;
    private MessageListAdapter messageListAdapter;
    private WebSocketClient client;

    //logCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // JSON flags to identify the kind of JSON response
    private static final String TAG_SELF = "self", TAG_NEW = "new",
            TAG_MESSAGE = "message", TAG_EXIT = "exit";


    Socket serverSocket = new Socket();
    private ConnectivityManager connect;

    @Override
    public void onCreate(Bundle savaedInstance) {

        from_server = (TextView) findViewById(R.id.from_server);
        search = (EditText) findViewById(R.id.editText_search); // search contact here
        searchBTN = (Button) findViewById(R.id.search_btn);
        title = (EditText) findViewById(R.id.editText_conv_title);
        sendBTN = (Button) findViewById(R.id.btn_send); // on click on sendBTN the message will be sent to recipient (user)
        message = (EditText) findViewById(R.id.editText_msg); // sent from user
        itemText = (TextView) findViewById(R.id.convFirstLine);
        side = false;
        threadsList = (ListView) findViewById(R.id.listView_chat);

        super.onCreate(savaedInstance);
        setContentView(R.layout.activity_chat);


        utils = new Utils(getApplicationContext());

        // Getting the person name from previous screen
        Intent i = getIntent();
        name = i.getStringExtra("name");

        this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

//      search for contact
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.list_of_contacts);
                Intent i = new Intent(getApplicationContext(), ContactsList.class);
                startActivity(i);
            }
        }); // first clickListener

        itemText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN &&
                        keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                    return sendChatMessage();
                }

                return false;
            }
        });
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // send message to web socket server
                sendChatMessageToServer(utils.getSendMessageJSON(message.getText()
                        .toString()));

                // clear text field after sending
                message.setText("");
                sendChatMessage();
            }
        });

        SocketConfig socketConfig = null;
        client = new WebSocketClient(URI.create(socketConfig.URL_WEBSOCKET + URLEncoder.encode(name)), new WebSocketClient.Listener() {

            @Override
            public void onConnect() {
                showToast("Connected");

            }

            //            * on receiving the message from web socket server
            @Override
            public void onMessage(String message) {
                Log.d(TAG, String.format("Got binary message: %s", message));
                parseMessage(message);
            }

            @Override
            public void onMessage(byte[] message) {
                Log.d(TAG, String.format("Got String message! %s", bytesToHex(message)));
//             message in JSON format
                parseMessage(bytesToHex(message));
            }

            @Override
            public void onDisconnect(int code, String reason) {
                String message = String.format(Locale.CANADA, "Disconnected! code: %d Reason: %s", code, reason);
                showToast(message);
                utils.storeSessionId(null);
            }
            @Override
        public void onError(Exception e){
                Log.e(TAG, "Error! ", e);
            }
        }, null);
            client.connect();
        }

    private void sendMessageToServer(){
        if (client!= null && client.isConnected()) {
            client.send(message.getText().toString());
        }
    }

    /*
    * Parsing the JSON file, it will be received by server.
    * The tag flag identifies the intent which client has sent the message.
    * if flag == self then it is from the person otherwise
    * if flag == new it means new member has joined the group
    * if flag == message that is for new message received from server
    * if flag == exit a member left group conversation
    * */

    private void parseMessage(final String txtMessage){

        try{
            JSONObject jsonObject = new JSONObject(txtMessage);

            // JSONObj flag
            String flag = jsonObject.getString("flag");

            String message = "";
            //if jsonobj is self it contains session id
            if (flag.equalsIgnoreCase("sessionId")) {
                //get sessionid of json file
                String sessionId = jsonObject.getString("sessionId");
                // save sessionid
                utils.storeSessionId(sessionId);
                // error
                Log.e(TAG, "The Session ID is: " + utils.getSessionId());

            } else if (flag.equalsIgnoreCase(TAG_NEW)){
                // flag == new --> new person joined to group chat
                String name = jsonObject.getString("name");
                message = jsonObject.getString("message");

                // get number of members online in group
                String onlineCounts = jsonObject.getString("onlineCounts");
                showToast(name + message + onlineCounts + ".Currently" + onlineCounts
                + "members online!!!");
            } else if(flag.equalsIgnoreCase(TAG_MESSAGE)){
                // flag == message --> new message received
                String fromname = name;
                message = jsonObject.getString("message");
                String sessionID = jsonObject.getString("sessionId");
                boolean isSelf = true;

                if (!sessionID.equalsIgnoreCase(utils.getSessionId())) {
                    fromname = jsonObject.getString("name");
                    isSelf = false;
                }
                Message msg = new Message(fromname, name, isSelf);
                appendMessage(msg);
            } else if (flag.equalsIgnoreCase(TAG_EXIT)){
                // if flag == exit; someone left the group
                String name = jsonObject.getString("name");
                message = jsonObject.getString("message");
                showToast(name + message);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ComponentName startService(Intent intent) {

        return super.startService(intent);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        // TODO: 2015-11-19 destroy server socket

        if ( client != null && client.isConnected()){
            client.disconnect();
            super.onDestroy();
        }
    }
    private void appendMessage(final Message m){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listMessages.add(m);
                messageListAdapter.notifyDataSetChanged();
            }
        });
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] inputS){
        char[] hexChars = new char[inputS.length * 2];
        for (int i=0; i<inputS.length ; i++){
            int v = inputS[i] & 0xFF;
            hexChars[i*2] = hexArray[v >>>4];
            hexChars[i*2+1] = hexArray[v & 0xFF];
        }
        return new String(hexChars);

    }

//        listMessages = new ArrayList<Message>();
//        if (network.isConnected()) {
//            chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_item_right);
//            threadsList.setAdapter(chatArrayAdapter);
//            message.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN
//                            && keyCode == KeyEvent.KEYCODE_ENTER) {
//                        return sendChatMessage();
//                    }
//                    return false;
//                }
//            });

    // endOf onCreate

    @Override
    protected void onStart(){
        super.onStart();
        serverSocket.getChannel().isOpen();
        threadsList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

//        set to scroll on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                threadsList.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    public boolean sendChatMessage(){
        // TODO: 2015-11-11 chat message to send:
        // https://trinitytuts.com/simple-chat-application-using-listview-in-android/
        ChatMessage object = new ChatMessage(side, message.getText().toString());
        chatArrayAdapter.add(this);
        message.setText("");
        side = !side;
        return true;
    }

    //    Register BroadcastReceiver
//    http://hmkcode.com/android-sending-receiving-custom-broadcasts/
    @Override
    protected void onResume() {
        super.onResume();
        if (serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    public void newChat(){
        message.setText("");
        search.setText("");
    }

//    * Method to send message to web oscket
//    *
//    *

    private void sendChatMessageToServer(String message){
        if (client != null && client.isConnected()) {
            client.send(message);
        }
        }
    private void showToast(String txt){
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG);
    }
    }

