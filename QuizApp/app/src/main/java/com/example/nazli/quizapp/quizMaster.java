package com.example.nazli.quizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nazli.quizapp.util.DatabaseHandler;

/**
 * Created by nazlimedghalchi on 2015-10-17.
 */
public class quizMaster {
    private String _userId;
    private String _password;
    private String _email;
    private Context c;

    public quizMaster(String username, String password){
        _email = username;
        _userId = username;
        _password = password;
    }
    public boolean registerQM (){

    }
    public String getUserId(){
        return _email;
    }
    public String getPassword(){
        return _password;
    }
    DatabaseHandler db = new DatabaseHandler(c);
    db.
    public void newQuiz(){

    }
    public  newQuiz addQuiz(){

    }
    public newQUestion addQuestion(){

    }
    public newUser addQuizTaker(){

    }

}
