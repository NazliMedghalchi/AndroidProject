package com.example.nazli.quizapp.util;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nazli.quizapp.R;

/**
 * Created by nazlimedghalchi on 2015-10-14.
 */
public class quizMasterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizmaster, container, false);
    }
}
