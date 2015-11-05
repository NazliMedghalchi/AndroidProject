package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

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

    }
//    remove group
    public void delGroup () {

    }
}
