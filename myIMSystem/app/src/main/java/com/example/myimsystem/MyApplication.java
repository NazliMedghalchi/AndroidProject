package com.example.myimsystem;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
            super.onCreate();
    }
    public String UID="0000";
    public ClientThread clientThread = new ClientThread();
}
