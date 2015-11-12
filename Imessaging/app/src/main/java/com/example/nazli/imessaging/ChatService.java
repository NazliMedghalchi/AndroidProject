package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public class ChatService extends Activity {


    private Client client; // receiver

//    String title = ((EditText) findViewById(R.id.contact_list_title)).toString();
    EditText search = (EditText) findViewById(R.id.editText_search); // search contact here
    Button searchBTN = (Button) findViewById(R.id.search_btn);

    Button sendBTN = (Button) findViewById(R.id.btn_send); // on click on sendBTN the message will be sent to recipient (user)
    EditText message = (EditText) findViewById(R.id.editText_msg); // sent from user
    TextView itemText = (TextView) findViewById(R.id.convFirstLine);
    Boolean side = false;

    ContentValues values = new ContentValues();
    String textMSG = message.toString();

    ListView threadsList = (ListView) findViewById(R.id.listView_chat);
    ChatArrayAdapter chatArrayAdapter;

//  EditText thread = (EditText) findViewById(R.id.threadText); // received from user

    Socket serverSocket = new Socket();


    private ConnectivityManager connect =  (ConnectivityManager)
            this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);


    @Override
    public ComponentName startService(Intent service) {
        HTTPgetRequest httPgetRequest = new HTTPgetRequest();
        switch (httPgetRequest.doInBackground()) {

        }
        return super.startService(service);
    }

    @Override
    public void onCreate(Bundle main) {
        super.onCreate(main);
        setContentView(R.layout.activity_chat);


        NetworkInfo network = connect.getActiveNetworkInfo();
        connect.getActiveNetwork();

        if (network.isConnected()) {
            chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_item_sent);
            threadsList.setAdapter(chatArrayAdapter);
            message.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN
                            && keyCode == KeyEvent.KEYCODE_ENTER) {
                        return sendChatMessage();
                    }
                    return false;
                }
            });
        }

        // search for contact
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*TODO later for creating contacts fragment displaying saved contacts
            * permission is added
            * http://developer.android.com/training/contacts-provider/retrieve-details.html
            *
            * In order to simplify, just using string entered in editText view
            * Hopefully user enters correct number
            * */

                ContactsContract contactsContract = new ContactsContract();
            }
        }); // first clickListener

        Client client  = new Client();
        IntentFilter intentFilter = new IntentFilter("com.example.nazli.Imessaging");

/*    The setOnkeyListener is for, if the number is in contacts list saved on phone, then make send btn clickable
    Otherwise it is not clickable
    The listener for sendBTN takes message and pass it to upStream
*/
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
                sendChatMessage();
            }
        });
        threadsList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

//        set to scroll on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                threadsList.setSelection(chatArrayAdapter.getCount()-1);
            }
        });

    } // endOf onCreate

    @Override
    public void onStart(){
        super.onStart();
        serverSocket.getChannel().isOpen();
    }

    public boolean sendChatMessage(){
        // TODO: 2015-11-11 chat message to send: https://trinitytuts.com/simple-chat-application-using-listview-in-android/
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
}


