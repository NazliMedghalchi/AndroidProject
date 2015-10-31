package com.example.nazli.imessaging;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static com.example.nazli.imessaging.R.layout.list_of_conversations;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */
public class Conversation extends ListFragment {
     public interface conversationList {
         public void onSelectConv(long rowID);
         public void addNewConv ();

     }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(, container, savedInstanceState);
    }
