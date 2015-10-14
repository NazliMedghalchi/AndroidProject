package com.example.nazli.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.ls.LSException;

import java.net.PasswordAuthentication;
import java.util.List;

/**
 * Created by nazlimedghalchi on 15-10-08.
 */
public class LoginMain extends Activity{
    private List<String> QuizTakers;
    private PasswordAuthentication passwordAuthenticationsQM;
    private List<String> QuizMasters;
    private PasswordAuthentication passwordAuthenticationsQT;

    private Button signinButtonQT;
    private Button signinButtonQM;
    final EditText userQT = (EditText) findViewById(R.id.userQT);
    final EditText userQM = (EditText) findViewById(R.id.userQM);
    final EditText passQT = (EditText) findViewById(R.id.passQT);
    final EditText passQM = (EditText) findViewById(R.id.passQM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void setUserQuizTaker(List<String> userQT) {
        if (!QuizTakers.contains(this.userQT)){
            AlertDialog.Builder errorUserQT = new AlertDialog.Builder(this);
            errorUserQT.setMessage(R.string.error_username_QT);
            errorUserQT.setTitle("Username not found");
            errorUserQT.setPositiveButton("Try Again", null);
            errorUserQT.create().show();
        }
        else if (passwordAuthenticationsQT.contains(this.passQT))
            setContentView(R.layout.fragment_quizmaster);
        else {
            AlertDialog.Builder errorLogin = new AlertDialog.Builder(this);
            errorLogin.setMessage(R.string.error_login_QT);
            errorLogin.setTitle("Password error");
            errorLogin.setPositiveButton("Try Again", null);
            errorLogin.create().show();
        }

    }

    PasswordAuthentication(this.userQT, this.passQT);
}
