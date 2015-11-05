package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public class ChatService extends Activity {

    String title = ((EditText) findViewById(R.id.textView_conv_title)).toString();
    EditText search = (EditText) findViewById(R.id.editText_search);
    Button searchBTN = (Button) findViewById(R.id.search_btn);
    ListView chatThreads = (ListView) findViewById(R.id.listView_chat);
    EditText message = (EditText) findViewById(R.id.editText_msg);
    Button sendBTN = (Button) findViewById(R.id.btn_send);

    ContentValues values = new ContentValues;
    String textMSG;

//    The following snippet is for, if the number is in contacts, then make send btn clickable
//    Otherwise it is not clickable
//    The listener for sendBTN takes message and pass it to upStream
    @Override
    public void onCreate(Bundle main) {
        super.onCreate(main);
        setContentView(R.layout.activity_chat);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsContract.Contacts contactsContract = new ContactsContract;
                if (contactsContract.NAME_RAW_CONTACT_ID.equals(search.toString())) {
                    sendBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            values.put(textMSG , message.toString());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart(){

    }


}
