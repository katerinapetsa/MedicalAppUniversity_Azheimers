package com.example.ehealth.lab.university.disease.description;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehealth.lab.university.R;

/**
 * This class contains information about "What is Myasthenia Gravis".
 *
 * @author Stavroula Kousparou
 *
 */

public class WhatFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mg_information, container, false);
        return view;
    }

}
