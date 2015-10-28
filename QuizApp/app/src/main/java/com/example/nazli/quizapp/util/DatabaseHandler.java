package com.example.nazli.quizapp.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TableRow;

import com.example.nazli.quizapp.R;

import java.sql.SQLDataException;
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

    // Columns in quiz Master and Taker tables
    USER_QT = "usserQT",
    USER_QM = "userQM",
    PASS_QT = "passQT",
    PASS_QM = "passQM",

    // Columns in quiz table
    QUIZ_ID = "label_quiz",
    QUIZ_ATTEMPT = "quiz_attempt",
//    QUIZ_LENGHT = "quiz_length", // quiz_length is based on number of questions


    // Columns in question table
    QST_ID = "qst_id",
    CORRECT_ANSWR = "correct_answr",
    INCORRECT1_ANSWR = "incorrect1_answr",
    INCORRECT2_ANSWR = "incorrect2_answr",
    INCORRECT3_ANSWR = "incorrect3_answr",
    QST_TIMER = "qst_timer",
    QST_ANSWR = "qst_answr"; // qst_answr := question correct answer
//    MIN_TIME = "min_time",
//    MAX_TIME = "max_time";

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
                USER_QM + "STRING PRIMARY KEY," +
                PASS_QM + "STRING" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_quizTaker +
                "(" +
                USER_QT + "STRING PRIMARY KEY," +
                PASS_QT + "STRING" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_quiz +
                        "(" +
                        QUIZ_ID + "INTEGER PRIMARY KEY AUTOINCREAMENT," +
                        QUIZ_ATTEMPT + "INTEGER," +
                        CORRECT_ANSWR + "STRING" +
                        INCORRECT1_ANSWR + "STRING" +
                        INCORRECT2_ANSWR + "STRING" +
                        INCORRECT3_ANSWR + "STRING" +
                        ")"
        );
        db.execSQL("CREATE TABLE " + TABLE_question + "(" +
                        QST_ID + "INTEGER PRIMARY KEY," +
                        QUIZ_ID + "FOREIGN KEY" +
                        QST_TIMER + "TIME," +
                        QST_ANSWR + "STRING," +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quizTaker);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quizMaster);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_quiz);
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_question);
        onCreate(db);
        db.close();
    }
    // Add new quiz taker to database
    public void newQuizT(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Add and check user
        ContentValues valuesQT = new ContentValues();
        valuesQT.put(USER_QT, R.id.userQT);
        valuesQT.put(PASS_QT, R.id.passQT);
        db.insert(TABLE_quizTaker, null, valuesQT);
    }

    private String getUserId(){
        return USER_QT;
    }
    private String getPassword(){
        return PASS_QT;
    }
    // Add new quiz to db
    public void newQuiz(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesQuiz = new ContentValues();
        valuesQuiz.put(QUIZ_ID, R.id.textView_question);
        valuesQuiz.put(QST_TIMER, R.id.chronometer_quiz);
        valuesQuiz.put(QUIZ_ATTEMPT, R.id.textView_question);
        valuesQuiz.put(CORRECT_ANSWR, R.id.correct_textView);
//        valuesQuiz.put(CORRECT_ANSWR, ); //correct answer

        db.insert(TABLE_quiz, null, valuesQuiz);

    }

    // Delete requested user from DB
    public void deleteUser(String userID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(TABLE_quizTaker, USER_QT + "=?", new String[]{userID});
    }
    // Add a row to Table_question
    public void newQuestion(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.get(TABLE_quiz, );
    }
}