package com.example.nazli.quizapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by nazlimedghalchi on 2015-10-17.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quizApp",
            TABLE_quizMaster = "quizMaster",
            TABLE_quizTaker = "quizTaker",
            TABLE_quiz ="quiz",
            TABLE_question = "question",
            USER_QT = "usserQT",
            USER_QM = "userQM",
            PASS_QT = "passQT",
            PASS_QM = "passQM",
            QUIZ_ID = "label_quiz",
            QUIZ_TIMER = "quiz_timer",
            QUIZ_ATTEMPT = "quiz_attempt",
    // quiz_length is based on number of questions
            QUIZ_LENGHT = "quiz_length",
            QST_ID = "qst_id",
            QST_TIMER = "qst_timer",
    // qst_answr = question correct answer
            QST_ANSWR = "qst_answr",
            MIN_TIME = "min_time",
            MAX_TIME = "max_time";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_quizMaster + "("+ USER_QM + "STRING PRIMARY KEY," + PASS_QM + "STRING)");
        db.execSQL("CREATE TABLE " + TABLE_quizTaker + "("+ USER_QT + "STRING PRIMARY KEY," + PASS_QT + "STRING)");
        db.execSQL("CREATE TABLE " + TABLE_quiz + "(" + QUIZ_ID + "INTEGER PRIMARY KEY AUTOINCREAMENT," + QUIZ_TIMER + "TIME," + QUIZ_ATTEMPT
        + "INTEGER," + QUIZ_LENGHT + "INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_question + "(" + QST_ID + "INTEGER," + QST_TIMER + "TIME," + QST_ANSWR + "STRING,"
                + MIN_TIME + "TIME," + MAX_TIME + "TIME)");
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("UPDATE TABLE " + TABLE_quizTaker + "SET " + USER_QT + );
    }

}
