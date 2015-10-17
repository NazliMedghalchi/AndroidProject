package com.example.nazli.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;
import com.google.android.gms.cast.Cast;

import java.net.PasswordAuthentication;
import java.util.Hashtable;


/**
 * Created by nazlimedghalchi on 15-10-08.
 */
public class LoginMain extends Activity{
    private Hashtable <String, String> QuizTakers = new Hashtable<>();
    private PasswordAuthentication passwordAuthenticationsQM;
    private Hashtable<String, String > QuizMasters = new Hashtable<>();
    private PasswordAuthentication passwordAuthenticationsQT;

    final EditText userQT = (EditText) findViewById(R.id.userQT);
    final EditText userQM = (EditText) findViewById(R.id.userQM);
    final EditText passQT = (EditText) findViewById(R.id.passQT);
    final EditText passQM = (EditText) findViewById(R.id.passQM);
    Button signinButtonQT = (Button) findViewById(R.id.signinButtonQT);
    signinButtonQT.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



            @Override
            public void onClick(View v) {
                if (checkUserQuizTaker()) {
                    setContentView(R.layout.fragment_quiz);
                }
            }
        Button signinButtonQM = (Button) findViewById(R.id.signInButtonQM);
        View.OnClickListener siginQMListener = new View.OnClickListener()



    }
//
//        //username and password check
//        private void registerUser(){
//            QuizTakers.put(this.userQT.getText().toString(), this.passQT.getText().toString());
//        }

    }
    private boolean checkUserQuizTaker() {
        if (!QuizTakers.contains(this.userQT)){
            AlertDialog.Builder errorUserQT = new AlertDialog.Builder(signin_error);
            errorUserQT.setMessage(R.string.error_username_QT);
            errorUserQT.setTitle("Username not found");
            errorUserQT.setPositiveButton("Try Again", null);
            errorUserQT.create().show();
        }
        else if (passwordAuthenticationsQT.equals(this.passQT)){
            QuizTakers.put(this.userQT.getText().toString(), this.passQT.getText().toString());
            return true;
        }
        else {
            AlertDialog.Builder errorLogin = new AlertDialog.Builder(this);
            errorLogin.setMessage(R.string.error_login_QT);
            errorLogin.setTitle("Password error");
            errorLogin.setPositiveButton("Try Again", null);
            errorLogin.create().show();
        }
        return false;
    }

    public boolean setUserQuizMaster() {
        //incorrect  username
        if (!QuizTakers.contains(this.userQM)){
            AlertDialog.Builder errorUserQM = new AlertDialog.Builder();
            errorUserQM.setMessage(R.string.error_username_QT);
            errorUserQM.setTitle("Username not found");
            errorUserQM.setPositiveButton("Try Again", null);
            errorUserQM.create().show();
        }
        //correct username and password
        else if (passwordAuthenticationsQT.equals(this.passQT))
            return true;
        //incorrect username and password
        else {
            AlertDialog.Builder errorLogin = new AlertDialog.Builder;
            errorLogin.setMessage(R.string.error_login_QT);
            errorLogin.setTitle("Password error");
            errorLogin.setPositiveButton("Try Again", null);
            errorLogin.create().show();
        }
        return false;
    }

//    private void registerUser(){
//        QuizTakers.put(this.userQT.getText().toString(), this.passQT.getText().toString());
//    }

