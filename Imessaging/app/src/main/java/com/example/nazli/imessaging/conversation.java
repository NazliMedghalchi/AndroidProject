package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */
public class Conversation extends ListActivity {


    ListView clist =  (ListView) findViewById(R.id.listView_conv);


    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        setContentView(R.layout.list_of_conversations);
    }

    // search a contact to send new message
    //
    public void searchContact() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor contactsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String phoneNumber = findViewById(R.id.contacts).toString() ;
        Cursor contacts = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                new String[] {ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[] {String.valueOf(ContactsContract.RawContacts.CONTACT_ID)},null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchContact();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(Bundle.EMPTY);
    }

    @Override
    protected void onDestroy() {
        super.onStart();
    }
}