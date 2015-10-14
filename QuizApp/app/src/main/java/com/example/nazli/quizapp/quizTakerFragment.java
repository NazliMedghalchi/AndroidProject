package com.example.nazli.quizapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nazlimedghalchi on 2015-10-14.
 */
public class quizTakerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.fragment_quiztaker, container, savedInstanceState);
    }
}
