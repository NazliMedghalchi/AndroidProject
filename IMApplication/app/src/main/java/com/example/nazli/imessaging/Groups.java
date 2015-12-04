package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by nazlimedghalchi on 2015-11-05.
 **/
/*
* Groups table stores each group detail information
* inorder to join or quit a group the connection will be removed from
* usergroup table which has  user_id and group_id as FKs
* */

    // Error on Database for query requesting Group activity in Intent

public class Groups extends Activity {

    // TODO: 2015-11-05 groups from DB
    ListView groups;
    TextView groupTitle;
    TextView groupId;
    TextView groupMem;

    String[] groupWidgets;
    String[] groupCol;

    DatabaseHelper db;
    Cursor groupAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_groups);

        final Context context = getApplicationContext();
        db = new DatabaseHelper(context);
        groupTitle = (TextView)findViewById(R.id.groupTitle);
        groupId = (TextView)findViewById(R.id.groupID);
        groupMem = (TextView)findViewById(R.id.groupMem);

        final String gTitle = "",
                gId = "",
                gMem = "";
        groupWidgets = new String[]{
                gTitle,
                gId,
                gMem
        };
        groupCol = new String[]{
                db.group_title,
                db.group_id,
                db.group_mem
        };

        showAllGroups(context);
//        Toast.makeText(getApplicationContext(), "List of Group is shown!", Toast.LENGTH_LONG);

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
//                        Context context = getApplicationContext();
                        joinGroup(groupWidgets, groupCol, context);
                        showAllGroups(getApplicationContext());
                    }
                });

                quitGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Context context = getApplicationContext();
                        leaveGroup(gId, context);
                    }
                });

                showGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Context context = getApplicationContext();
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

    @Override
    protected void onDestroy(){
        db.close();
        super.onDestroy();
    }

//     Display Groups in listview, calls method in Db for adapter
    public void showAllGroups(Context context){
        Cursor groupAdapter;
        String[] fromDB = new String[] {db.group_id, db.group_title, db.group_mem};
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
