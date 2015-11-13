package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
    DatabaseHelper db;
    ListView convList;
    ContactsContract contactsContract;


    public CursorAdapter getContactsContract() {
        convList = (ListView) findViewById(R.id.listView_conv);
        contactsContract = new ContactsContract();
        db = new DatabaseHelper(this);
// TODO: 2015-11-12 bind accounts to clist
        CursorAdapter cursorAdapter = new CursorAdapter(getApplicationContext(),cursor) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }
        };

        convList.setAdapter(cursorAdapter);

        return cursorAdapter;
    }

}
