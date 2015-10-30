package com.example.nazli.imessaging;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;

/**
 * Created by nazlimedghalchi on 2015-10-29.
 */
public class databaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iMessage";
    private static final int DATABASE_VERION = 1;

    // tables and containing columns
    private final String CONVERSATION = "conversation";
    private final String ACCOUTNS = "account";
    private final String GROUPS = "groups";
    private final String THREADS = "threads";
    private final String FRIENDS = "friends";

    // account's columns
    private final String USERNAME = "useername";
    private final String USER_ID = "user_id";
    private final String PASSWORD = "password";
    private final String FRIEND_LIST = "friend_list";
    private final String GROUP_LIST = "group_list";
    private final String USER_STATUS = "user_status";

    // group's columns
    private final String GROUP_ID = "group_id";
    private final String GROUP_MEMBERS = "group_members";
    private final String GROUP_OWNER = "group_owner";
    private final String GROUP_TITLE = "group_title";

    // conversation columns
    private final String CONV_ID = "conv_id";
    private final String CONV_NAME = "conv_name";
    private final String THREAD_ID = "thread_id";

    // threads columns
    private final String TIME = "time";
    private final String DATE = "date";
    private final String SENDER = "sender";
    private final String RECEIVER = "receiver";
    private final String STATUS = "status";
    private final String CONTENT = "content";
    private final String ATTACHEMENT = "attachment";

    // friends columns
    private final String USER1 = "user_1";
    private final String USER2 = "user_2";
    private final String FLIST_ID = "flist_id";

    public databaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ACCOUTNS + "(" +
                        USER_ID + "INTEGER PRIMARY KEY" +
                        USERNAME + "STRING" +
                        PASSWORD + "STRING" +
                        FRIEND_LIST + "INTEGER" +
                        GROUP_LIST + "INTEGER" +
                        USER_STATUS + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + GROUPS + "(" +
                        GROUP_ID + "INTEGER PRIMARY KEY" +
                        GROUP_MEMBERS + "INTEGER FOREIGN KEY" +
                        GROUP_OWNER + "STRING" +
                        GROUP_TITLE + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + CONVERSATION + "(" +
                        CONV_ID + "INTEGER PRIMARY KEY" +
                        THREAD_ID + "INTEGER FOREIGN KEY" +
                        CONV_NAME + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + THREADS + "(" +
                        THREAD_ID + "INTEGER PRIMARY KEY" +
                        SENDER + "STRING" +
                        RECEIVER + "STRING" +
                        DATE + "STRING" +
                        TIME + "STRING" +
                        STATUS + "STRING" +
                        CONTENT + "TEXT" +
                        ATTACHEMENT + "INTEGER" + ")"// 0 or 1
        );
        // list of friends will be saved as text from a json file
        db.execSQL("CREATE TABLE " + FRIENDS + "(" +
                        FLIST_ID + "INTEGER PRIMARY KEY" +
                        USER1 + "INTEGER" +
                        USER2 + "TEXT" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUTNS);
        db.execSQL("DROP TABLE IF EXISTS " + CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + THREADS);
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS);
        onCreate(db);
    }
    // start a new conversation
    public void newConversation (){

    }

    // join to group
    public void joinGroup () {

    }

    // leave a group
    public void leaveGroup () {

    }

    // delete a member from group
    public void deleteMember () {

    }

    // delete thread
    public void delThread (){

    }

    // show group
    public void showGroupMem(){

    }

}
