package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by nazlimedghalchi on 2015-11-05.
 */
public class ContactList extends Activity {
    CursorAdapter cursorAdapter;
    Cursor cursor;
    DatabaseHelper db = new DatabaseHelper(this);
    ListView convList = (ListView) findViewById(R.id.listView_conv);
    ContactsContract contactsContract = new ContactsContract();


    public CursorAdapter getContactsContract() {
        DatabaseHelper db = new DatabaseHelper(this);

        Intent intent = this.getIntent();
        return cursorAdapter;
    }

}
