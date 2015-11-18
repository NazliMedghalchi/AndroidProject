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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    String groupTitle = ((TextView) findViewById(R.id.groupTitle)).toString();
    String groupId = ((TextView) findViewById(R.id.groupID)).toString();
    String groupMem = ((TextView) findViewById(R.id.groupMem)).toString();

    String[] groupWidgets = new String[]{
            groupTitle,
            groupId,
            groupMem
    };
    String[] groupCol = new String[]{
            db.GROUP_TITLE,
            db.GROUP_ID,
            db.GROUP_MEM
    };

    Cursor groupAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_groups);

        Context context = getApplicationContext();
        showAllGroups(context);
        Toast.makeText(getApplicationContext(), "List of Group is shown!", Toast.LENGTH_LONG);

        groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Button joinGroup = (Button) findViewById(R.id.addGroup);
                final Button quitGroup = (Button) findViewById(R.id.quitGroup);
                Button showGroup = (Button) findViewById(R.id.showGroup);

                // On each item selection, user can choose the action by pressing any of
                // buttons as an option


                joinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = getApplicationContext();
                        joinGroup(groupWidgets, groupCol, context);
                        showAllGroups(getApplicationContext());
                    }
                });


                quitGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = getApplicationContext();
                        leaveGroup(groupId, context);
                    }
                });

                showGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = getApplicationContext();
                        showAllGroups(context);
                    }
                });
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
    }

//     Display Groups in listview, calls method in Db for adapter
    public void showAllGroups(Context context){
        Cursor groupAdapter;
        String[] fromDB = new String[] {db.GROUP_ID, db.GROUP_TITLE, db.GROUP_MEM};
        int[] toGUI = new int[] {R.id.groupID, R.id.groupTitle, R.id.groupMem};
        groupAdapter = db.showAllGroup(context);
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.group_selector,
                groupAdapter, fromDB, toGUI, 0);
        groups.setAdapter(simpleCursorAdapter);
    }

    public void leaveGroup (String groupId, Context context){
        db.leaveGroup(groupId, context);
    }

    public void setGroupAdapter(Cursor groupAdapter) {
        this.groupAdapter = groupAdapter;
    }

    // add new group
    // access database through joinGroup Method
    public void joinGroup(String[] groupCol, String[] groupWidgets, Context context) {
        db.joinGroup(groupCol, groupWidgets, context);
    }

}
