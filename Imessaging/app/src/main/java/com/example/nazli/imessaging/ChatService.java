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
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public class ChatService extends Activity {

    // receiver
    private Client client;
    IntentFilter intentFilter;

//    String title = ((EditText) findViewById(R.id.contact_list_title)).toString();
    EditText search = (EditText) findViewById(R.id.editText_search);
    Button searchBTN = (Button) findViewById(R.id.search_btn);
    Button sendBTN = (Button) findViewById(R.id.btn_send); // on click on sendBTN the message will be sent to recipient (user)
    EditText message = (EditText) findViewById(R.id.editText_msg); // sent from user
    TextView itemText = (TextView) findViewById(R.id.convFirstLine);

    ContentValues values = new ContentValues();
    String textMSG = message.toString();

    ListView threadsList = (ListView) findViewById(R.id.listView_chat);
//    EditText thread = (EditText) findViewById(R.id.threadText); // received from user

    Socket serverSocket = new Socket();

    @Override
    public ComponentName startService(Intent service) {
        client.getSocket();
        return super.startService(service);
    }

    @Override
    public void onCreate(Bundle main) {
        super.onCreate(main);
        setContentView(R.layout.activity_chat);
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
                    sendMessage();
                    return true;
                }

                return false;
            }
        });
    } // endOf onCreate

    @Override
    public void onStart(){
        serverSocket.getChannel().isOpen();


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

    public void sendMessage(){
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put(textMSG , message.toString());

            }
        });

    }


}
