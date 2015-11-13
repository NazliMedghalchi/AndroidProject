package com.example.nazli.imessaging;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by nazlimedghalchi on 2015-10-29.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "iMessage";
    public static final int DATABASE_VERION = 1;

    // tables
    public final String CONVERSATION = "Conversation";
    public final String ACCOUNTS = "account";
    public final String GROUPS = "groups";
    public final String THREADS = "threads";
    public final String FRIENDS = "friends";
    public final String USERGROUP = "usergroup";

    // account's columns
    public final String USERNAME = "useername";
    private final String _ID = "_id";
    public final String PASSWORD = "password";
    public final String FRIEND_LIST = "friend_list";
    public final String GROUP_LIST = "group_list";
    public final String USER_STATUS = "user_status";

    // group's columns
    public final String GROUP_ID = "group_id";
    public final String GROUP_OWNER = "group_owner";
    public final String GROUP_TITLE = "group_title";

    // Conversation columns
    public final String CONV_ID = "conv_id";
    public final String CONV_NAME = "conv_name";
    public final String THREAD_ID = "thread_id";

    // threads columns
    public final String TIME = "time";
    public final String DATE = "date";
    public final String SENDER = "sender";
    public final String STATUS = "status";
    public final String CONTENT = "content";

    // friends columns
    public final String USER1 = "user_1";
    public final String USER2 = "user_2";
    public final String FLIST_ID = "flist_id";

    // usergroup columns
    // this table is connecting users to groups
    // therefore both user_id and group_id are foreign keys
    
    public final String USERGROUP_ID = "usergroup_id";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ACCOUNTS + "(" +
                        _ID + "INTEGER PRIMARY KEY" +
                        GROUP_ID + "INTEGER FOREIGN KEY" +
                        USERNAME + "STRING" +
                        PASSWORD + "STRING" +
                        FRIEND_LIST + "INTEGER" +
                        GROUP_LIST + "INTEGER" +
                        USER_STATUS + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + GROUPS + "(" +
                        GROUP_ID + "INTEGER PRIMARY KEY" +
                        _ID + "INTEGER FOREIGN KEY" +
                        GROUP_OWNER + "STRING" +
                        GROUP_TITLE + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + USERGROUP + "(" +
                        USERGROUP_ID + "INTEGER PRIMARY KEY"
                        GROUP_ID + "INTEGER FOREIGN KEY" +
                        _ID + "INTEGER FOREIGN KEY" + ")"
        );
//        Conversation table is joined to thread table which is message table
        db.execSQL("CREATE TABLE " + CONVERSATION + "(" +
                        CONV_ID + "INTEGER PRIMARY KEY" +
                        THREAD_ID + "INTEGER FOREIGN KEY" +
                        CONV_NAME + "STRING" + ")"
        );
        db.execSQL("CREATE TABLE " + THREADS + "(" +
                        THREAD_ID + "INTEGER PRIMARY KEY" +
                        _ID + "INTEGER FOREIGN KEY" +
                        SENDER + "STRING" +
                        DATE + "STRING" +
                        TIME + "STRING" +
                        STATUS + "STRING" +
                        CONTENT + "TEXT" + ")"
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
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + THREADS);
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS);
        onCreate(db);
    }
    
    // start a new Conversation
    public void newConversation (Context c, ContentValues values){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(CONVERSATION, null, values);

        // Toast message on save
        if (!sqLiteDatabase.isOpen()) {
            Toast toast = Toast.makeText(c, "Error on database opening", Toast.LENGTH_LONG);
            toast.show();
            // position toast message
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        }

    }

//    Show Accounts as Contacts
    public ContentValues getACCOUNTS() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor c = sqLiteDatabase;
        ContentValues values = new ContentValues();
//        values.putAll(sqLiteDatabase.execSQL("SELECT * FROM " + ACCOUNTS));
        return values;
    }

    //  Create a new Group
    public void addGroup(ContentValues values){


    }

    // join to group
//    public void joinGroup (ContentValues values) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.insert(GROUPS, null, values);
//    }

    // leave a group
    public void leaveGroup (String groupID) {
        SQLiteDatabase sql = this.getWritableDatabase();
        String GROUPID = groupID.toString();
//        Cursor cursor = sql.rawQuery("DELETE FROM " + USERGROUP + "WHERE GROUP_ID = " + GROUPID);

    }

//    join to an existing Group
    public void joinGroup (ContentValues value){
        SQLiteDatabase sqlite = this.getWritableDatabase();
        sqlite.insert(USERGROUP, null, value);
    }

    // delete thread
    public void delThread (){

    }

    // show group
    public Cursor showGroup(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + GROUPS , null);
        return cursor;
    }

    // show list of all conversations
    public Cursor showConversation(Context context){
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT " + CONV_ID + "FROM " + CONVERSATION, null);
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
        c = sqLiteDatabase.rawQuery("SELECT * FROM " + CONVERSATION, null);
        return c;
    }
}
