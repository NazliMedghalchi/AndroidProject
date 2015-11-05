package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by nazlimedghalchi on 2015-11-05.
 */
public class Groups extends Activity {

//    // TODO: 2015-11-05 groups from DB
    ListView groups = (ListView) findViewById(R.id.listView_groups);
    DatabaseHelper bd = new DatabaseHelper(this);
    Cursor cursor;
    CursorAdapter convAdapter = new CursorAdapter().newView(getApplicationContext(), cursor, null);

