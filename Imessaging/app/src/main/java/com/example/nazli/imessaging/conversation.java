package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.ContentProvider;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static com.example.nazli.imessaging.R.layout.list_of_conversations;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */
public class Conversation extends  Activity {

    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        
        setContentView(R.layout.list_of_conversations);
    }



}

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(, container, savedInstanceState);
// }
