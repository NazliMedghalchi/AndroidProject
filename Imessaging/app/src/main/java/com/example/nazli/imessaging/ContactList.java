package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
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
public class ContactList extends ListActivity {
    CursorAdapter cursorAdapter;
    Cursor cursor;
    DatabaseHelper db;
    ListView convList;
    ContactsContract contactsContract;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.list_of_contacts);

        cursor = this.getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);

    }

    public Cursor getContactsContract() {
        convList = (ListView) findViewById(R.id.listView_conv);
        contactsContract = new ContactsContract();
        db = new DatabaseHelper(this);
// TODO: 2015-11-12 bind accounts to clist

        return cursor;
    }

}
