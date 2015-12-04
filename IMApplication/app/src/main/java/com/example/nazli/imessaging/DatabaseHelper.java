package com.example.nazli.imessaging;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by nazlimedghalchi on 2015-10-29.
 **/
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iMessage";
    public static final int DATABASE_VERION = 1;

    // tables
    public final String conversation = "CONVERSATION ";
    public final String accounts = "ACCOUNTS ";
    public final String groups = "GROUPS ";
    public final String threads = "THREADS ";
    public final String friends = "FRIENDS ";
    public final String usergroup = "USERGROUP ";

    // account's columns
    public final String username = "USEERNAME ";
    private final String _id = "_ID ";
    public final String password = "PASSWORD ";
    public final String friend_list = "FRIEND_LIST ";
    public final String group_list = "GROUP_LIST ";
    public final String user_status = "USER_STATUS ";

    // group's columns
    public final String group_id = "GROUP_ID ";
    public final String group_owner = "GROUP_OWNER ";
    public final String group_title = "GROUP_TITLE ";
    public final String group_mem = "GROUP_MEM ";

    // Conversation columns
    public final String conv_id = "CONV_ID ";
    public final String conv_name = "CONV_NAME ";
    public final String thread_id = "THREAD_ID ";

    // threads columns
    public final String time = "TIME ";
    public final String date = "DATE ";
    public final String sender = "SENDER ";
    public final String status = "STATUS ";
    public final String content = "CONTENT ";

    // friends columns
    public final String user1 = "USER_1 ";
    public final String user2 = "USER_2 ";
    public final String flist_id = "FLIST_ID ";

    // usergroup columns
    // this table is connecting users to groups
    // therefore both user_id and group_id are foreign keys

    public final String usergroup_id = "USERGROUP_ID ";
    private SQLiteDatabase db;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(" CREATE TABLE " + accounts + "( " +
                        _id + " INTEGER PRIMARY KEY, " +
                        group_id + " VARCHAR, " + //Foreign key
                        username + "STRING, " +
                        password + " STRING, " +
                        user_status + " STRING" + ");"
        );
        // group_mem is to keep number of members not their names
        db.execSQL("CREATE TABLE " + groups + "( " +
                        group_id + " VARCHAR PRIMARY KEY, " +
                        _id + " VARCHAR, " + //foreign key
                        group_mem + " VARCHAR, " +
                        group_title + "VARCHAR, " +
                        conv_id + "TEXT" + " );" //foreign key
        );
        db.execSQL("CREATE TABLE " + usergroup + "( " +
                        usergroup_id + " INTEGER PRIMARY KEY, " +
                        group_id + "VARCHAR, " + //foreign key
                        _id + "INTEGER" + "); " //foreign key
        );
//        Conversation table is joined to thread table which is message table
        db.execSQL("CREATE TABLE " + conversation + "( " +
                        conv_id + "INTEGER PRIMARY KEY, " +
                        conv_name + "VARCHAR" + " ); "
        );
        // each  RECEIVER has many senders - based on this the thread table is designed
        db.execSQL("CREATE TABLE " + threads + "( " +
                        thread_id + "INTEGER PRIMARY KEY, " +
                        conv_id + "INTEGER, " + //foreign key
                        sender + "VARCHAR, " +
                        date + "VARCHAR, " +
                        time + "VARCHAR, " +
                        status + "VARCHAR, " +
                        content + "TEXT" + " );"
        );
        // list of friends will be saved as text from a json file
        db.execSQL("CREATE TABLE " + friends + "(" +
                        flist_id + " INTEGER PRIMARY KEY, " +
                        user1 + " INTEGER, " +
                        user2 + " TEXT" + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + accounts);
        db.execSQL(" DROP TABLE IF EXISTS " + conversation);
        db.execSQL(" DROP TABLE IF EXISTS " + threads);
        db.execSQL(" DROP TABLE IF EXISTS " + friends);
        db.execSQL(" DROP TABLE IF EXISTS " + groups);
        db.execSQL(" DROP TABLE IF EXISTS " + usergroup);
        onCreate(db);
    }
    
    // start a new Conversation
    public void newConversation (Context c, ContentValues values){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(conversation, null, values);

        // Toast message on save
        if (!sqLiteDatabase.isOpen()) {
            Toast toast = Toast.makeText(c, "Error on database opening", Toast.LENGTH_LONG);
            toast.show();
            // position toast message
//            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        }
        sqLiteDatabase.close();

    }

//    Show Accounts as Contacts
    public Cursor getACCOUNTS() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorAdapter = null;
        if (cursorAdapter!= null){
            cursorAdapter.moveToFirst();
        }
        sqLiteDatabase.rawQuery(" SELECT * " + " FROM " + accounts, new String[]{username, user_status});
        return cursorAdapter;
    }

    //  Create a new Group
    // TODO: 2015-11-17 done 
    public void addGroup(String groupId, String userId){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_id, userId);
        values.put(group_id, groupId);
        sqLiteDatabase.insert(usergroup, null, values);
        sqLiteDatabase.close();
    }

    // join to group
//    public void joinGroup (ContentValues values) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.insert(GROUPS, null, values);
//    }

    // TODO: 2015-11-17 done
    // leave a group
    public void leaveGroup (String groupId, Context context) {
        SQLiteDatabase sql = this.getWritableDatabase();
        sql.delete(usergroup, group_id + " = '" + groupId + "' ", null);
        sql.close();
    }

    //  join to an existing Group
    // TODO: 2015-11-17 done
    public void joinGroup (String[] groupWidegets, String[] groupCols, Context context){
        SQLiteDatabase sqlite = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(groupWidegets[0], groupCols[0]);
        values.put(groupWidegets[1], groupCols[1]);
        values.put(groupWidegets[2], groupCols[2]);
        sqlite.insert(groups, null, values);
        Toast.makeText(context, "Successfully joined to the group", Toast.LENGTH_SHORT);
        sqlite.close();
    }

    // delete thread
    public void delThread (){

    }

    // show all groups in listview
    public Cursor showAllGroup(Context context){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursorAdapter = null;
        if (cursorAdapter!= null){
            cursorAdapter.moveToFirst();
        }
        sqLiteDatabase.rawQuery(" SELECT * " + " FROM " + groups,
                new String[] {group_id, group_title, group_mem}, null);
        return cursorAdapter;
    }


    // show list of all conversations
    public Cursor showConversation(Context context){
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery(" SELECT " + conv_id + "FROM " + conversation, null, null);
        if (!sqLiteDatabase.isOpen()){
            // make a toast message
            Toast toast = Toast.makeText(context, "Error on database opening", Toast.LENGTH_LONG);
            toast.show();
            // position toast message
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0 , 0);
        }
        return cursor;
    }

    // Show all conversations
    public Cursor showAllConversation(Context context){
        Cursor c;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        c = sqLiteDatabase.rawQuery(" SELECT * FROM " + conversation, null);
        return c;
    }
}
