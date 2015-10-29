package com.example.nazli.quizapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.GpsStatus;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nazli.quizapp.util.DatabaseHandler;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by nazlimedghalchi on 2015-10-17.
 */
public class quizMaster extends Activity{
//    private Hashtable<String, String> user_id = new Hashtable<String, String>();

    private Context applicationcontext;

    private int quiz_id;
    private String quiz_label  = ((TextView)findViewById(R.id.label_quiz)).getText().toString();
    private Chronometer quiz_lenght = ((Chronometer) findViewById(R.id.chronometer_quiz));
    private Arrays multipleChoices;
    private String correct_ans;
    private String incorrect_ans1;



    public quizMaster(String username, String password){
        // instance of database
        DatabaseHandler db =  new DatabaseHandler(applicationcontext);
        RadioButton quizDB = ((RadioButton) findViewById(R.id.Quiz_DB));
        RadioButton quizTakerDB = ((RadioButton) findViewById(R.id.QT_DB));

        // Load quizzes database in listView
        quizDB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setContentView(R.layout.fragment_quiz);
                newQuiz();
            }
        });
        db.newQuizT();

        // Load quiz Takers database in the listView
        quizTakerDB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setContentView(R.layout.quiz_taker_db);
                DatabaseHandler db = new DatabaseHandler(applicationcontext);
                db.newQuizT();
            }
        });
    }

    // add new quiz to Database
    public void newQuiz(){
        ((TextView) findViewById(R.id.label_quiz)).setText("");
        ((Chronometer) findViewById(R.id.chronometer_quiz)).setText("");
        ((TextView) findViewById(R.id.quizResult)).setText("");
        ((TextView) findViewById(R.id.textView_question)).setText("");
        ((RadioButton) findViewById(R.id.radioButton1_choice)).setText("");
        ((RadioButton) findViewById(R.id.radioButton2_choice)).setText("");
        ((RadioButton) findViewById(R.id.radioButton3_choice)).setText("");
        ((RadioButton) findViewById(R.id.radioButton4_choice)).setText("");
        ((TextView) findViewById(R.id.score_textView)).setText("");
        ((TextView) findViewById(R.id.textview_attemps)).setText("");
        ((TextView) findViewById(R.id.textView_question)).setText("");

        // Enable all radio buttons
        for (RadioButton button: multipleChoices) {
            button.setText(((RadioButton) findViewById(R.id.radioButton1_choice)).getText().toString());
            button.setEnabled(true);
        }
        // Save to database
        DatabaseHandler db = new DatabaseHandler(this);
        db.newQuiz();

    }

    public void deleteUser(){
        View.OnClickListener listener;
        Button del = findViewById(R.id.button_del_user).setOnClickListener(listener);

    }


}
