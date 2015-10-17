package com.example.nazli.quizapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by nazlimedghalchi on 2015-10-14.
 */
public class quizTaker extends Activity {

    private TextView lable_quiz = (TextView) findViewById(R.id.label_quiz);
    private Chronometer chronometer_quiz = (Chronometer) findViewById(R.id.chronometer_quiz);
    private TextView textView_question = (TextView) findViewById(R.id.textView_question);
    private RadioButton[] guessAnswer; // row of multiple choice
    private LinearLayout questionNumberTextView;
    private LinearLayout linearlayoutH_result = (LinearLayout) findViewById(R.id.linearlayoutH_result);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //show quiz questions
        lable_quiz.setText(getResources().getString(R.string.));
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

        for (RadioButton row : guessAnswer){
            row.setText(String.valueOf(getText(toString(R.array.multiple_choice))));
            row.setClickable(true);
            View.OnClickListener radioButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            };
        }


    }

    private void onCreateQuiz (){
        lable_quiz.setText(getTex);
    }
}
