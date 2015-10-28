package com.example.nazli.quizapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import java.util.Arrays;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nazli.quizapp.util.DatabaseHandler;

import java.lang.reflect.Array;

/**
 * Created by nazlimedghalchi on 2015-10-14.
 */
public class quizTaker extends Activity {

    //UI components
    private TextView lable_quiz = (TextView) findViewById(R.id.label_quiz);
    private Chronometer chronometer_quiz = (Chronometer) findViewById(R.id.chronometer_quiz);
    private TextView textView_question = (TextView) findViewById(R.id.textView_question);
    private RadioButton row1_choice = (RadioButton) findViewById(R.id.row1_choice);
    private RadioButton row2_choice = (RadioButton) findViewById(R.id.row2_choice);
    private RadioButton row3_choice = (RadioButton) findViewById(R.id.row3_choice);
    private RadioButton row4_choice = (RadioButton) findViewById(R.id.row4_choice);
    private RadioButton[] guessAnswer; // row of multiple choice
    private LinearLayout questionNumberTextView;
    private LinearLayout linearlayoutH_result = (LinearLayout) findViewById(R.id.linearlayoutH_result);
    private TextView score_textview = (TextView) findViewById(R.id.score_textView);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //show quiz questions
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.
        return inflater.inflate(R.layout.fragment_quiz, container, false);


        // get references to GUI components
        guessAnswer = new RadioButton[4];
        guessAnswer[0] =
                (RadioButton) findViewById(R.id.radioButton1_choice);
        guessAnswer[1] =
                (RadioButton) findViewById(R.id.radioButton2_choice);
        guessAnswer[2] =
                (RadioButton) findViewById(R.id.radioButton3_choice);
        guessAnswer[3] =
                (RadioButton) findViewById(R.id.radioButton4_choice);

        for (final RadioButton row : guessAnswer){
            row.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (row.getText() ==
                }
            });
            row.setClickable(true);
            View.OnClickListener radioButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            };
        }


    }

//    private void onCreateQuiz (){
//        lable_quiz.setText();
//    }
}
