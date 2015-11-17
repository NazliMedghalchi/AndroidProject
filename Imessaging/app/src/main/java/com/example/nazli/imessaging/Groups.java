package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by nazlimedghalchi on 2015-11-05.
 *
 * Groups table stores each group detail information
 * inorder to join or quit a group the connection will be removed from
 * usergroup table which has  user_id and group_id as FKs
 *
 * */

public class Groups extends Activity {

    // TODO: 2015-11-05 groups from DB
    ListView groups;
    DatabaseHelper db = new DatabaseHelper(this);
    int[] groupWidgets = new int[]{
            R.id.groupTitle,
            R.id.groupID,
            R.id.groupMem
    };
    String[] groupCol = new String[]{
            db.GROUP_ID,
            db.GROUP_OWNER,
            db.GROUP_TITLE
    };


    Cursor groupAdapter = db.showGroup();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_groups);
        groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Button addGroup = (Button) findViewById(R.id.addGroup);
                final Button quitGroup = (Button) findViewById(R.id.quitGroup);
                Button showGroup = (Button) findViewById(R.id.showGroup);


//      On each item selection, user can choose the action by pressing any of
//      buttons as an option
                addGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinGroup();
                    }
                });
                quitGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leaveGroup();
                    }
                });
                showGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showGroup();
                    }
                });
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
    }

//     Display Groups in listview
    public void displayGroup(){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.id.listView_groups,
                groupAdapter, groupCol, groupWidgets, 0);
        setGroupAdapter(groupAdapter);
    }
    public void setGroupAdapter(Cursor groupAdapter) {
        this.groupAdapter = groupAdapter;
    }

//    add new group
    public void joinGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        values.put("GROUP_ID", R.id.groupID);
        values.put("_ID", R.id.textView_usrname);
        db.joinGroup(values, getApplicationContext());
    }
//    remove group
    public void leaveGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
//        db.leaveGroup(// FIXME: 2015-11-11 )
    }

//    Join to a new Group
    public void showGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
//        values.put(// FIXME: 2015-11-11 )
//                db.joinGroup(values);

    }
}
