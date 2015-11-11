package com.example.nazli.imessaging;

import android.app.Activity;
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
 */
public class Groups extends Activity {
    //    // TODO: 2015-11-05 groups from DB
    ListView groups = (ListView) findViewById(R.id.listView_groups);
    DatabaseHelper db = new DatabaseHelper(this);
    int[] groupWidgets = new int[]{
            R.id.groupName,
            R.id.groupID,
            R.id.groupMem
    };
    String[] groupCol = new String[]{
            db.GROUP_ID,
            db.GROUP_MEMBERS,
            db.GROUP_TITLE
    };


    Cursor groupAdapter = db.showGroup();

    @Override
    public void onStart(){
//        On each item selection, user can choose the action by pressing any of
//        buttons as an option

        groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Button addGroup = (Button) findViewById(R.id.addGroup);
                final Button quitGroup = (Button) findViewById(R.id.quitGroup);
                Button showGroup = (Button) findViewById(R.id.showGroup);

                addGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addGroup();
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

    // Display Groups in listview
    public void displayGroup(){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.id.listView_groups,
                groupAdapter, groupCol, groupWidgets, 0);
        setGroupAdapter(groupAdapter);
    }
    public void setGroupAdapter(Cursor groupAdapter) {
        this.groupAdapter = groupAdapter;
    }

//    add new group
    public void addGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
        LayoutInflater inflater = new LayoutInflater(this, ) {
            @Override
            public LayoutInflater cloneInContext(Context newContext) {
                return null;
            }
        };
        Intent intent = new Intent(this, ChatService.class);
        startActivity(intent);
        db.addGroup()// FIXME: 2015-11-11 )
    }
//    remove group
    public void leaveGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
        db.leaveGroup(// FIXME: 2015-11-11 )
    }

//    Join to a new Group
    public void showGroup () {
        DatabaseHelper db = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        values.put(// FIXME: 2015-11-11 )
                db.joinGroup(values);

    }
}
