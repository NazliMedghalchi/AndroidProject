package com.example.nazli.quizapp;

/**
 * Created by nazlimedghalchi on 2015-10-17.
 */
public class quizMasterContact {
    private String _userId;
    private String _password;
    private String _email;

    public quizMasterContact(String username, String password){
        _email = username;
        _userId = username;
        _password = password;
    }
    public boolean registerQM (String _userId, String _password){
        this._userId = _userId;
        this._password = _password;
        return true;
    }
    public String getUserId(){
        return _email;
    }
    public String getPassword(){
        return _password;
    }
}
