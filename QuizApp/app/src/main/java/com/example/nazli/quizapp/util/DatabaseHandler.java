package com.example.nazli.quizapp.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TableRow;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by nazlimedghalchi on 2015-10-17.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // make database instance Singleton instance across entire application lifetime
    private static DatabaseHandler sInstance;

    private static final String DATA_PATH = "./assests/quizApp";
    private final int BUFFER_SIZE = 8 * 1024;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quizApp",
            TABLE_quizMaster = "quizMaster",
            TABLE_quizTaker = "quizTaker",
            TABLE_quiz = "quiz",
            TABLE_question = "question",
    // fields in quiz Master and Taker tables
    USER_QT = "usserQT",
            USER_QM = "userQM",
            PASS_QT = "passQT",
            PASS_QM = "passQM",
    // fields in quiz table
    QUIZ_ID = "label_quiz",
            QUIZ_TIMER = "quiz_timer",
            QUIZ_ATTEMPT = "quiz_attempt",
    // quiz_length is based on number of questions
    QUIZ_LENGHT = "quiz_length",
    // fields in question table
    QST_ID = "qst_id",
            QST_TIMER = "qst_timer",
    // qst_answr := question correct answer
    QST_ANSWR = "qst_answr",
            MIN_TIME = "min_time",
            MAX_TIME = "max_time";

    // The following part is copied from https://guides.codepath.com/android/Local-Databases-with-SQLiteOpenHelper
    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    // throw exception on database open error
//    public SQLiteDatabase openDatabes(){
//        try {
//            String databasePath = DATA
//            if (DT)
//        }
//    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_quizMaster +
                "(" +
                USER_QM + "STRING PRIMARY KEY," + PASS_QM + "STRING" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_quizTaker +
                "(" +
                USER_QT + "STRING PRIMARY KEY," + PASS_QT + "STRING" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_quiz +
                "(" +
                QUIZ_ID + "INTEGER PRIMARY KEY AUTOINCREAMENT," + QUIZ_TIMER + "TIME," + QUIZ_ATTEMPT +
                "INTEGER," + QUIZ_LENGHT + "INTEGER" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_question +
                "(" +
                QST_ID + "INTEGER," + QST_TIMER + "TIME," + QST_ANSWR + "STRING," +
                MIN_TIME + "TIME," + MAX_TIME + "TIME" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quizTaker);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quizMaster);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quiz);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_question);
       // Add and check user
        ContentValues values = new ContentValues();
        values.put(USER_QT, R.id.user_QT);
        values.put(PASS_QT, R.id.pass_QT);


        db.close();
    }
}